package RISK;

import javax.swing.*;

public class LoadingWithSAX implements LoadingStrategy{
    public void loadGame(String mapName,RiskView view,RiskModel model)
    {
        XMLHandler handler = null;
        try {
            handler = new XMLHandler(mapName);
            String fileName = view.getFileName();
            if(fileName==null||fileName.isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter the file name you want to load! Try to load it again.", "Empty file name", JOptionPane.ERROR_MESSAGE);
                return;
            }
            fileName+="_"+model.getMapName();
            handler.importXMLFileByName(fileName);
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(null,"You haven't save any file with this name under this map. Please check and try again.","File Not Found",JOptionPane.ERROR_MESSAGE);
            return;
        }

        model = handler.getModel();
        model.addView(view);
        model.reload();
    }
}
