package RISK;

import javax.swing.*;

/**
 * The type Load new map dialog.
 */
public class LoadNewMapDialog {
    private JPanel newMapPanel = new JPanel();
    private MapType[] type = {MapType.Classic,MapType.Seaport,MapType.Alcatraz,MapType.Invalid_Isolated_Territory,MapType.Invalid_Isolated_Groups};
    private JComboBox comboBox = new JComboBox(type);

    /**
     * Instantiates a new Load new map dialog.
     */
    public LoadNewMapDialog(){
        newMapPanel.add(comboBox);

        JOptionPane.showMessageDialog(null,newMapPanel,"Select the map you want to play",JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Get map name string.
     *
     * @return the string
     */
    public String getMapName(){
        return ((MapType)comboBox.getSelectedItem()).getFileName();
    }

    /**
     * The enum Map type.
     */
    enum MapType{
        /**
         * Classic map type.
         */
        Classic("OriginRiskMap"),
        /**
         * Seaport map type.
         */
        Seaport("Map1"),
        /**
         * Alcatraz map type.
         */
        Alcatraz("Map2"),
        /**
         * Invalid isolated territory map type.
         */
        Invalid_Isolated_Territory("Invalid1"),
        /**
         * Invalid isolated groups map type.
         */
        Invalid_Isolated_Groups("Invalid2"),;

        private String fileName;

        /**
         * Constructor for Maptype
         */
        MapType(String fileName) {
            this.fileName = fileName;
        }

        /**
         * get map name
         * @return map name
         */
        private String getFileName(){
            return fileName;
        }
    }
}


