import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PoligonosApp extends Application {

    // Exemplo de lista de polígonos representados por listas de pontos (x, y)
    private List<List<double[]>> pontosPoligonos = List.of(
        List.of(new double[]{0, 0}, new double[]{100, 0}, new double[]{50, 50}), // Triângulo
        List.of(new double[]{200, 200}, new double[]{300, 200}, new double[]{300, 300}, new double[]{200, 300}) // Quadrado
    );

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Criar polígonos e adicioná-los ao root
        pontosPoligonos.stream()
            .map(pontos -> {
                Polygon polygon = new Polygon();
                polygon.getPoints().addAll(
                    pontos.stream()
                        .flatMapToDouble(p -> Stream.of(p[0], p[1]).mapToDouble(Double::doubleValue))
                        .boxed()
                        .collect(Collectors.toList())
                );
                return polygon;
            })
            .forEach(root.getChildren()::add);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Polígonos com JavaFX");
        primaryStage.show();

        // Exibir perímetro e tipo de cada polígono
        pontosPoligonos.forEach(p -> {
            double perimetro = calcularPerimetro(p);
            String tipo = identificarTipo(p);
            System.out.println("Polígono: " + tipo + ", Perímetro: " + perimetro);
        });
    }

    // Método para calcular o perímetro de um polígono
    private double calcularPerimetro(List<double[]> pontos) {
        return IntStream.range(0, pontos.size())
            .mapToDouble(i -> {
                double[] p1 = pontos.get(i);
                double[] p2 = pontos.get((i + 1) % pontos.size()); // Fechando o polígono
                return Math.sqrt(Math.pow(p2[0] - p1[0], 2) + Math.pow(p2[1] - p1[1], 2));
            })
            .sum();
    }

    // Método para identificar o tipo de polígono com base no número de vértices
    private String identificarTipo(List<double[]> pontos) {
        return switch (pontos.size()) {
            case 3 -> "Triângulo";
            case 4 -> "Quadrilátero";
            case 5 -> "Pentágono";
            case 6 -> "Hexágono";
            default -> "Polígono com " + pontos.size() + " lados";
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
