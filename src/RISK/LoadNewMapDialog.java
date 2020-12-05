package RISK;

import javax.swing.*;

public class LoadNewMapDialog {
    private JPanel newMapPanel = new JPanel();
    private MapType[] type = {MapType.Classic,MapType.Seaport,MapType.Invalid};
    private JComboBox comboBox = new JComboBox(type);

    public LoadNewMapDialog(){
        newMapPanel.add(comboBox);

        JOptionPane.showMessageDialog(null,newMapPanel,"Select the map you want to play",JOptionPane.PLAIN_MESSAGE);
    }

    public String getMapName(){
        return ((MapType)comboBox.getSelectedItem()).getFileName();
    }

    enum MapType{
        Classic("OriginRiskMap"),Seaport("Map1"),Invalid("Invalid");

        private String fileName;
        MapType(String fileName) {
            this.fileName = fileName;
        }

        private String getFileName(){
            return fileName;
        }
    }
}


