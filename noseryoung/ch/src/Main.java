import java.util.ArrayList;
import java.util.List;

public class Main {

    static void main() {
        List<Snack> snacks = new ArrayList<>(
            List.of(
                    new Snack("Monster energy Ultra", 2, 5.f),
                    new Snack("Coca Cola", 10, 3.5f),
                    new Snack("Eistee", 8, 3.5f),
                    new Snack("Red bull", 6, 5.f),
                    new Snack("Snickers", 13, 4.6f),
                    new Snack("Bubble Gum",20,1.5f),
                    new Snack("Chips", 6, 5f),
                    new Snack("Wasser", 10, 3.5f),
                    new Snack("Nüsse", 10, 4f)
            )
    );

        UI ui = new UI(snacks);
        ui.Menu();
    }
}
