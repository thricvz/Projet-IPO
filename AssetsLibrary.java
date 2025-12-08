import java.awt.*;
import java.util.*;
import  javax.swing.*;


class AssetsLibrary{
  private static AssetsLibrary instance;
  private HashMap<String,Image> assetStorage;   


  public static AssetsLibrary Instance(){
    if(instance == null){
      instance = new AssetsLibrary();
    } 
    return instance; 
  }

  private AssetsLibrary(){
       this.assetStorage = new HashMap<String,Image>(); 
       this.addAssetToLibrary("floor","/home/eric/Projects/TPProjet/assets/ground.png"); 
       this.addAssetToLibrary("wall","/home/eric/Projects/TPProjet/assets/wall.png"); 

  };

  private void addAssetToLibrary(String assetName,String fileLocation){
     try{
        ImageIcon asset = new ImageIcon(fileLocation);        
        Image asset_img = asset.getImage();
        
        this.assetStorage.put(assetName,asset_img);        

     }catch(Exception e){
        System.out.println("Couldn't load the asset");
     } 
  }

  public Image getAsset(String assetName){
      return this.assetStorage.get(assetName);
  };


}
