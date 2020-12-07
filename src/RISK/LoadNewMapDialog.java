package RISK;

import javax.swing.*;

public class LoadNewMapDialog {
    private JPanel newMapPanel = new JPanel();
    private MapType[] type = {MapType.Classic,MapType.Seaport,MapType.Alcatraz,MapType.Invalid_Isolated_Territory,MapType.Invalid_Isolated_Groups};
    private JComboBox comboBox = new JComboBox(type);

    public LoadNewMapDialog(){
        newMapPanel.add(comboBox);

        JOptionPane.showMessageDialog(null,newMapPanel,"Select the map you want to play",JOptionPane.PLAIN_MESSAGE);
    }

    public String getMapName(){
        return ((MapType)comboBox.getSelectedItem()).getFileName();
    }

    enum MapType{
        Classic("OriginRiskMap"),Seaport("Map1"),Alcatraz("Map2"),Invalid_Isolated_Territory("Invalid1"),Invalid_Isolated_Groups("Invalid2"),;

        private String fileName;
        MapType(String fileName) {
            this.fileName = fileName;
        }

        private String getFileName(){
            return fileName;
        }
    }
}


