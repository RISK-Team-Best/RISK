package ToolsAndTries;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class MapEditorV4Model extends DefaultListModel {
    private final XMLDOMWriter writer = new XMLDOMWriter();
    private final Document board= writer.createEmptyBoard();
    private final Element BoardInfo = board.createElement("BoardInfo");
    private final Element ContinentList = board.createElement("ContinentList");
    private final Element TerritoriesList = board.createElement("TerritoriesList");
    private JList<String> view;
    private JList<String> subView;
    private Map<String,DefaultListModel<String>> subModels = new HashMap<>();
    private Map<String,Element> continentMap = new HashMap<>();
    private MapEditorV4 frame;
    public MapEditorV4Model(MapEditorV4 frame)
    {
        this.frame = frame;
        board.appendChild(BoardInfo);
        BoardInfo.appendChild(ContinentList);
        BoardInfo.appendChild(TerritoriesList);
    }

    public void addContinent(String name)
    {
        String bonus = JOptionPane.showInputDialog("bonus");
        Element continent = board.createElement("newContinent");
        continent.setAttribute("name",name);
        continent.setAttribute("bonus",bonus);
        ContinentList.appendChild(continent);
        continentMap.put(name,continent);

        addElement(name);
        subModels.put(name,new DefaultListModel<>());
    }

    public void generateBoard()
    {
        writer.generateMapWithDoc(board);

    }

    public void addTerritory(int x, int y) {
        String continent = view.getSelectedValue();
        if (continent==null){
            System.out.println("No Continent Selected");
            return;
        }
        String name = JOptionPane.showInputDialog("Territory Name");
        if (name==null){return;}
        Element territory = board.createElement("Territory");
        territory.setAttribute("x",String.valueOf(x));
        territory.setAttribute("y",String.valueOf(y));
        territory.setTextContent(name);
        TerritoriesList.appendChild(territory);

        Element memberTerritory = board.createElement("MemberTerritory");
        memberTerritory.setTextContent(name);
        continentMap.get(view.getSelectedValue()).appendChild(memberTerritory);

        frame.addButton(name,x,y);
        DefaultListModel<String> subViewModel = subModels.get(continent);
        subView.setModel(subViewModel);
        subViewModel.addElement(name);

    }

    public JList<String> getView() {
        return view;
    }

    public void setView(JList<String> view) {
        this.view = view;
    }

    public JList<String> getSubView() {
        return subView;
    }

    public void setSubView(JList<String> subView) {
        this.subView = subView;
    }

    public void removeTerritory() {
        String s = subView.getSelectedValue();
        if(s==null){return;}
        DefaultListModel<String> temp = (DefaultListModel)subView.getModel();
        temp.removeElement(s);
        frame.removeButton(s);
        Node node = board.getElementsByTagName(s).item(0);
        node.getParentNode().removeChild(node);
    }

    public void changeSubmodel() {
        String continent = view.getSelectedValue();
        if (continent==null){
            System.out.println("No Continent Selected");
            return;
        }

        DefaultListModel<String> subViewModel = subModels.get(continent);
        subView.setModel(subViewModel);
    }

    public void setPath(String path) {
        BoardInfo.setAttribute("path",path);
    }
}
