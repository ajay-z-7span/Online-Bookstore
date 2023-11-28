package com.demo.onlinebookstore.exceptionhandler;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundExceptions extends Exception{

   private String msg = null;
   private HttpStatus status = null;


   public ResourceNotFoundExceptions(String msg, HttpStatus status){
       super(msg);
       this.msg =msg;
       this.status = status;
   }

}
