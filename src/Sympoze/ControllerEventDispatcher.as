package Sympoze
{
	import flash.events.EventDispatcher;
	import flash.events.NetStatusEvent;

	public class ControllerEventDispatcher extends EventDispatcher
	{
		private static var _instance:ControllerEventDispatcher=null;
		
		
		public function ControllerEventDispatcher()
		{
			//trace(‘new instance of singleton created’);
		}
		
		public static function getInstance():ControllerEventDispatcher
		{
			if(_instance==null){
				_instance=new ControllerEventDispatcher();
			}
			return _instance;
		}
		
		public function DispachConnection(infoObj:NetStatusEvent):void
		{
			trace("nc: "+infoObj.info.code+" ("+infoObj.info.description+")");
			if (infoObj.info.code == "NetConnection.Connect.Failed")
				dispatchEvent(new ControllerEvent(Constants.CONNECTFAILED));
				
			else if (infoObj.info.code == "NetConnection.Connect.Rejected")
				dispatchEvent(new ControllerEvent(Constants.CONNECTFAILED,infoObj));
				
			else if (infoObj.info.code == "NetConnection.Connect.Success")
			{//dispatch event for publish
				var event:ControllerEvent = new ControllerEvent(Constants.CONNECTSUCCESS,infoObj);
				event.data[Constants.publishName] = "1"; //TODO: take publishName from user?

				if (dispatchEvent(event))	
					trace("NetConnection.Connect.Success event dispached!");
				else 
					trace("NetConnection.Connect.Success dispached failed!");
			}
			
		}
		
		//public function DispachPublish(infoObj:Event):void
		
	}
}//package
