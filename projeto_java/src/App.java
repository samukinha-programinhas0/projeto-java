import controllers.FilmesControllers;

public class App {
    public static void main(String[] args) throws Exception {
        FilmesControllers controller = new FilmesControllers();
        controller.iniciar();
    }
}