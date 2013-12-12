package Sympoze
{
	import flash.events.Event;

	
	public class ControllerEvent extends Event
	{

		
		public var data:Object;
		
		public function ControllerEvent(type:String, dataObj:Object = null, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			this.data = dataObj;
			super(type, bubbles, cancelable);
		}
	}//ControllerEvent
	

	
}//package