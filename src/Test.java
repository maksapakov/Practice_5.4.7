public class Test {
    public static class Box<T> {
        private T object;

        public static <T> Box<T> getBox(T object) {
            return new Box<>();
        }
    }
}
