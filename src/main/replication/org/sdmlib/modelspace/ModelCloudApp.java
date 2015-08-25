package org.sdmlib.modelspace;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.InetAddress;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.sdmlib.javafx.FX;
import org.sdmlib.modelspace.ModelSpace.ApplicationType;
import org.sdmlib.modelspace.util.ModelCloudCreator;
import org.sdmlib.modelspace.util.TaskBoardCreator;

import de.uniks.networkparser.json.JsonIdMap;

public class ModelCloudApp extends Application
{
   public static void main(String... args)
   {
      launch(args);
   }

   private String location;
   private VBox root;
   private String userName;
   private String sessionId;
   private JsonIdMap idMap;
   private ModelCloud modelCloud;
   private ModelSpace space;
   private TextField acceptPortField;
   private HBox addServerBox;
   private TextField hostNameField;
   private TextField portNoField;
   private VBox modelSpaceProxiesVbox;
   private TaskBoard taskBoard;
   private ModelSpace taskSpace;
   private TaskLane myTaskLane;
   
   @Override
   public void start(Stage stage) throws Exception
   {
      if (location == null)
      {
         Parameters params = this.getParameters();
         List<String> parameters = params.getRaw();
         
         if (parameters.size() > 0)
         {
            location = parameters.get(0);
         }
         else
         {
            location = "modeldata";
         }
      }

      userName = "cloudServer";
      
      sessionId = userName + System.currentTimeMillis();

      idMap = ModelCloudCreator.createIdMap(sessionId);

      modelCloud = new ModelCloud();

      idMap.put(location, modelCloud);

      space = new ModelSpace(idMap, userName, ApplicationType.JavaFX).open(location + "/.cloudData");
      
      
      InetAddress ip = InetAddress.getLocalHost();
      String hostname = ip.getHostName();
      System.out.println("Your current IP address : " + ip);
      System.out.println("Your current Hostname : " + hostname);
      
      modelCloud.setHostName(hostname);
      
      Label hostNameLabel = new Label("Host name: " + hostname);
      
      Label label = new Label("accept port:");
      
      acceptPortField = new TextField();
      FX.bindTextFieldIntegerProperty(idMap, acceptPortField, modelCloud, ModelCloud.PROPERTY_ACCEPTPORT);
      
      
      HBox hBox = new HBox(8);
      hBox.getChildren().addAll(label, acceptPortField);
      
      Label otherServersLabel = new Label ("Other cloud servers:");
      
      VBox otherSeversVBox = new VBox(8);
      FX.bindListProperty(idMap, otherSeversVBox, modelCloud, ModelCloud.PROPERTY_SERVERS, ModelCloudProxyController.class);

      hostNameField = new TextField("hostName?");
      portNoField = new TextField("portNo?");
      Button button = new Button("Add");
      button.setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent arg0)
         {
            // create ModelCloudProxy and add to servers
            ModelCloudProxy modelCloudProxy = new ModelCloudProxy()
               .withHostName(hostNameField.getText());
            
            int portNo = Integer.parseInt(portNoField.getText());
            modelCloudProxy.setPortNo(portNo);
            modelCloud.withServers(modelCloudProxy);
         }
      });
      
      addServerBox = new HBox(8);
      addServerBox.getChildren().addAll(hostNameField, portNoField, button);
      
      // add listener for model space proxies
      
      modelSpaceProxiesVbox = new VBox(8);
      
      modelCloud.getPropertyChangeSupport().addPropertyChangeListener(ModelCloud.PROPERTY_MODELSPACES, 
         new ModelSpaceProxyListener(modelCloud, modelSpaceProxiesVbox));
      

      // open task board
      String laneName = modelCloud.getHostName() + modelCloud.getAcceptPort();
      
      String taskSessionId = laneName + "_" + System.currentTimeMillis();

      JsonIdMap taskIdMap = TaskBoardCreator.createIdMap(taskSessionId);
      
      taskBoard = new TaskBoard(modelCloud, taskIdMap);
      modelCloud.setTaskBoard(taskBoard);

      taskIdMap.put("taskBoard", taskBoard);

      taskSpace = new ModelSpace(taskIdMap, userName, ApplicationType.JavaFX)
      .open(location + "/.cloudTasks");
      
      myTaskLane = taskBoard.getOrCreateLane(modelCloud.getHostName(), modelCloud.getAcceptPort());
      modelCloud.setMyTaskLane(myTaskLane);
      
      VBox tasksVBox = new VBox(8);
      
      TaskLaneListener taskLaneListener = new TaskLaneListener(modelCloud, myTaskLane, tasksVBox);
      
      myTaskLane.getPropertyChangeSupport().addPropertyChangeListener(
         taskLaneListener);
      
      taskLaneListener.propertyChange(null);
      
      root = new VBox(8);
      root.setPadding(new Insets(24));
      root.getChildren().addAll(hostNameLabel, hBox, otherServersLabel, otherSeversVBox, addServerBox, modelSpaceProxiesVbox, tasksVBox);
      
      ScrollPane scrollPane = new ScrollPane(root);
      
      scrollPane.setStyle("-fx-background: white;");
      
      Scene scene = new Scene(scrollPane, 400, 600);
      
      stage.setScene(scene);
      
      stage.setTitle("Model Cloud " + location);
      
      stage.setOnCloseRequest(new EventHandler<WindowEvent>()
         {

         @Override
         public void handle(WindowEvent arg0)
         {
            Platform.exit();
            System.exit(0);
         }
         });

      stage.show();
      
      // open server socket
      PropertyChangeListener listener = new PropertyChangeListener()
      {
         @Override
         public void propertyChange(PropertyChangeEvent evt)
         {
            if (modelCloud.getAcceptPort() > 0)
            {
               ServerSocketHandler serverSocketHandler = new ServerSocketHandler(modelCloud);
               serverSocketHandler.start();
            }
         }
      };
      
      modelCloud.getPropertyChangeSupport().addPropertyChangeListener(ModelCloud.PROPERTY_ACCEPTPORT, listener);
      
      listener.propertyChange(null);
      
      // connect to other servers
      modelCloud.getPropertyChangeSupport().addPropertyChangeListener(ModelCloud.PROPERTY_SERVERS, new OtherServersListener(modelCloud));
      
      // open listener for root directory
      ModelDirListener modelDirListener = new ModelDirListener(null, modelCloud, location, "");
      modelDirListener.start();
   }

}
