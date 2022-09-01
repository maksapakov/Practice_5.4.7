import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IllegalArgumentException {
        byte[] intermediate = null;
        try (
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(output)) {

            oos.writeInt(3);
            oos.writeObject(new Animal("Dog"));
            oos.writeObject(new Animal("Cat"));
            oos.writeObject(new Animal("Mouse"));

            output.flush();
            intermediate = output.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(intermediate));
        Animal[] animals = deserializeAnimalArray(intermediate);
        System.out.println(Arrays.toString(animals));

    }

    public static Animal[] deserializeAnimalArray(byte[] data) throws IllegalArgumentException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        Animal[] animal;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            int animalCount = objectInputStream.readInt();
            animal = new Animal[animalCount];

            for (int i = 0; i < animalCount; i++) {
                animal[i] = (Animal) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException | ClassCastException | ArrayIndexOutOfBoundsException
                | NegativeArraySizeException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return animal;
    }

    static class Animal implements Serializable {

        private final String name;

        public Animal(String name) {
            this.name = name;
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Animal) {
                return Objects.equals(name, ((Animal) obj).name);
            }
            return false;
        }

    }
}
/*

    public static class IllegalArgumentException extends Exception {
        public IllegalArgumentException(String message) {
            super(message);
        }
    }
*/
