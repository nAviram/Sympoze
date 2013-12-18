package Sympoze
{
	import flash.events.EventDispatcher;
	import flash.events.NetStatusEvent;
	import flash.media.Camera;
	import flash.media.Microphone;
	import flash.media.Video;
	import flash.net.NetConnection;
	import flash.net.NetStream;
	import flash.system.System;
	
	import mx.controls.Alert;


	/*
	* File: Controller.as
	* 
	* 2013-12-10 Written by Aviram Netanel   
	*
	* Description:
	* This file contains all code required for Microphone & Camera operation
	* as well as handling with sending & recieving Video Streams
	*
	*/				
		
	public class Controller extends EventDispatcher
	{
		private var m_dispatcher:ControllerEventDispatcher=null;
		private var m_netConnection:NetConnection = null;
		
		private var m_StreamSender:NetStream = null;
		private var m_StreamReciever1:NetStream = null;
		private var m_StreamReciever2:NetStream = null;
		
		public var m_cam:Camera;
		public var m_mic:Microphone;
		public var m_vidChannel1:Video;
		public var m_vidChannel2:Video;
		public var m_myVideo:Video;
		
		public function Controller()
		{
			m_dispatcher = new ControllerEventDispatcher();
			init();
		}
	
		public function init():void 
		{
			startCamAndMic();
			
			if (connect(Constants.rtmpURL))//connect to server
			{
				
				//publish(Constants.publishName);
			}
			else //cannot connect
			{
				//TODO: handle cannot connect scenario
				return;
			}
			
			
		}
		
		/**
		 * @param: conStr - connection string
		 */
		public function connect(conStr:String):Boolean
		{
			if (conStr!=null)
			{
				m_netConnection = new NetConnection();
				try{
					// get status information from the NetConnection object:
					m_netConnection.addEventListener(NetStatusEvent.NET_STATUS, m_dispatcher.DispachConnection );
					
					m_dispatcher.addEventListener(Constants.CONNECTSUCCESS, publish);
					
					m_netConnection.connect(conStr); //connect to server
					
					//var timer = setInterval(foo, 3000);
					
					//IMPORTANT !!!
					
					
					dispatchEvent(new ControllerEvent(Constants.INIT));
					
				}catch(e:Error){
					trace("2", e.getStackTrace());	
				}
//				 if (!m_netConnection.connected){
//					 Alert.show("Could not Connect to: "+conStr);
//					 return false;
//				 }else
//				 	return true;
				return true;
			}else
				return false;
		}
		
		public function publish(event:ControllerEvent):void //,publishName:String):Boolean
		{
			try{
				
				var publishName:String;
				if (event && event.data && event.data["publishName"])
				{
					
				}
					
				
				m_StreamSender = new NetStream(m_netConnection);
				
				// set the buffer time to zero since it is chat
				m_StreamSender.bufferTime = 0;
				
				// publish the stream by name
				m_StreamSender.publish(publishName);
				
				// add custom metadata to the stream
				var metaData:Object = new Object();
				metaData["description"] = "Chat using VideoChat example."
				m_StreamSender.send("@setDataFrame", "onMetaData", metaData);
				
				// attach the camera and microphone to the server
				m_StreamSender.attachCamera(m_cam);
				m_StreamSender.attachAudio(m_mic);
				
				m_netConnection.call("login",null,publishName);//calls the server
				//return true;
			}
			catch(e:Error){
				trace("1", e.getStackTrace());
				//return false;
			}
			//return false;
		}
		
		
		public function startCamAndMic():void
		{
			//get camera & microphone
			m_cam =  Camera.getCamera();
			m_mic = Microphone.getMicrophone();
			
			// here are all the quality and performance settings that we suggest
			m_cam.setMode(240, 180, 15, false);
			m_cam.setQuality(0, 80);
			m_cam.setKeyFrameInterval(30);
			m_mic.rate = 11;
			m_mic.setSilenceLevel(0);
			
			
		}
	
	}//class
	
}//package