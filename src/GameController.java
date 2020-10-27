import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this Class is only a demonstration of MVC pattern,all Territory Buttons are controlled by lambda
 * skip Button is controlled by this
 */
public class GameController implements ActionListener {
    private Game model;
    public GameController(Game model){
        this.model =model;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        model.skipStage();

    }
}
