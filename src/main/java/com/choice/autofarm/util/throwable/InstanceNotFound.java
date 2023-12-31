package com.choice.autofarm.util.throwable;

public class InstanceNotFound extends Exception{

     public InstanceNotFound(){
         super("Main.getInstance() not found in AutoFarm");
     }

}
