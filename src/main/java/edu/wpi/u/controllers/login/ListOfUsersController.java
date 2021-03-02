package edu.wpi.u.controllers.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.u.users.Guest;
import edu.wpi.u.users.User;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeTableColumn;

import java.awt.event.MouseEvent;

public class ListOfUsersController {

    public JFXTreeTableView<User> userTableView = new JFXTreeTableView<User>();
    public JFXButton editSelectedButton;

    TreeTableColumn<User, String> treeTableColumn1 = new TreeTableColumn<>("userID");


  // @Override
  // public void handle(MouseEvent t)
  // {
  //     TableCell c;
  //     c = (TableCell) t.getSource();
  //     c.getIndex();
  //     PanelController.selectedItem = c.getIndex();}


    public void handleEditUser() {
    }
}
