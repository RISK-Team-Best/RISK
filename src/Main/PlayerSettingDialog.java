package Main;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PlayerSettingDialog{
    private JPanel settingPanel = new JPanel();

    private int column = 2;
    private int row;

    private String[] type = {"Player","AI"};

    private ArrayList<String> defaultNames = new ArrayList<>();

    private LinkedHashMap<JTextField,JComboBox> namesAndTypes = new LinkedHashMap<>();

    public PlayerSettingDialog(int playerNumbers){
        this.row = playerNumbers;

        defaultNames.add("Alexa");
        defaultNames.add("Benjamin");
        defaultNames.add("Chloe");
        defaultNames.add("Diego");
        defaultNames.add("Eric");
        defaultNames.add("George");

        settingPanel.setLayout(new GridLayout(row,column));

        for(int i = 0; i < playerNumbers; i++){
            JTextField tempTextFiled = new JTextField(defaultNames.get(i));
            JComboBox tempComboBox = new JComboBox<String>(type);
            settingPanel.add(tempTextFiled);
            settingPanel.add(tempComboBox);
            namesAndTypes.put(tempTextFiled,tempComboBox);
        }

        do {
            JOptionPane.showMessageDialog(null, settingPanel, "Enter Player Name and Type", JOptionPane.PLAIN_MESSAGE);
        }while(!checkAvailability());
    }

    public LinkedHashMap<String, Boolean> getFinalNamesAndIsAI() {
        LinkedHashMap<String,Boolean> finalNamesAndIsAI = new LinkedHashMap<>();

        for(JTextField key:namesAndTypes.keySet()){
            if(((String)namesAndTypes.get(key).getSelectedItem()).equals("AI")){
                finalNamesAndIsAI.put(key.getText(),true);
            }else{
                finalNamesAndIsAI.put(key.getText(),false);
            }
        }
        return finalNamesAndIsAI;
    }

    private boolean checkAvailability(){
        LinkedHashMap<String,String> finalNamesAndType = new LinkedHashMap<>();
        for(JTextField key: namesAndTypes.keySet()){
            int size = finalNamesAndType.size();
            finalNamesAndType.put(key.getText(),(String)namesAndTypes.get(key).getSelectedItem());
            if(finalNamesAndType.size()==size||key.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Cannot have repeat player's name! Or no name!","Name Repetition",JOptionPane.ERROR_MESSAGE);
                finalNamesAndType.clear();
                return false;
            }
        }
        return true;
    }
}
