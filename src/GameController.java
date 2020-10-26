import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener {
    private Game model;
    public GameController(Game model){
        this.model =model;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        model.handleButtonpressed(((TerritoryButton)e.getSource()));

    }
}
