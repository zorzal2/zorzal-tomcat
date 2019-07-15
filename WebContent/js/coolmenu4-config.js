/*** 
This is the menu creation code - place it right after you body tag
Feel free to add this to a stand-alone js file and link it to your page.
**/

//Menu object creation //----
oCMenu=new makeCM("oCMenu") //Making the menu object. Argument: menuname

oCMenu.frames = 0

//Menu properties   
oCMenu.pxBetween=0
oCMenu.fromLeft=10 
oCMenu.fromTop=50
oCMenu.rows=1 
oCMenu.menuPlacement="left" 
                                                             
oCMenu.offlineRoot="file:///C|/Inetpub/wwwroot/dhtmlcentral/projects/coolmenus/examples/" 
oCMenu.onlineRoot="" 
oCMenu.resizeCheck=1 
oCMenu.wait=300 
oCMenu.fillImg="images/menu/cm_fill.gif"
oCMenu.zIndex=400
oCMenu.pagecheck=0
oCMenu.checkscroll=0

//Background bar properties
oCMenu.useBar=1
oCMenu.barWidth="100%"
oCMenu.barHeight="menu" 
oCMenu.barClass="clBar"
oCMenu.barX=0 
oCMenu.barY=0
oCMenu.barBorderX=0
oCMenu.barBorderY=0
oCMenu.barBorderClass=""

//Level properties - ALL properties have to be spesified in level 0
oCMenu.level[0]=new cm_makeLevel() //Add this for each new level
oCMenu.level[0].width=200
oCMenu.level[0].height=25 
oCMenu.level[0].regClass="cmMenu"
oCMenu.level[0].overClass="cmMenuOver"
oCMenu.level[0].borderX=1
oCMenu.level[0].borderY=1
oCMenu.level[0].borderClass="cmMenuBorder"
oCMenu.level[0].offsetX=0
oCMenu.level[0].offsetY=0
oCMenu.level[0].rows=0
oCMenu.level[0].arrow=0
oCMenu.level[0].arrowWidth=0
oCMenu.level[0].arrowHeight=0
oCMenu.level[0].align="bottom"
oCMenu.level[0].filter="progid:DXImageTransform.Microsoft.Fade(duration=0.5)"
oCMenu.level[0].arrow="images/menu/Rarrow.gif"
oCMenu.level[0].arrowWidth=12
oCMenu.level[0].arrowHeight=12

//EXAMPLE SUB LEVEL[1] PROPERTIES - You have to specify the properties you want different from LEVEL[0] - If you want all items to look the same just remove this
oCMenu.level[1]=new cm_makeLevel() //Add this for each new level (adding one to the number)
oCMenu.level[1].width=oCMenu.level[0].width-2
oCMenu.level[1].height=22
oCMenu.level[1].regClass="cmItem"
oCMenu.level[1].overClass="cmItemOver"
oCMenu.level[1].borderX=1
oCMenu.level[1].borderY=1
oCMenu.level[1].align="right"	   // Direcci?n que toman los submen?s
oCMenu.level[1].offsetX=-5
oCMenu.level[1].offsetY=0
oCMenu.level[1].borderClass="cmItemBorder"

/******************************************
CM_ADD-IN - hideselectboxes (last updated: 11/13/02)
IE5+ and NS6+ only - ignores the other browsers

Because of the selectbox bug in the browsers that makes 
selectboxes have the highest z-index whatever you do 
this script will check for selectboxes that interfear with
your menu items and then hide them. 

Just add this code to the coolmenus js file
or link the cm_addins.js file to your page as well.
*****************************************/
if(bw.dom&&!bw.op){
  makeCM.prototype.sel=0
  makeCM.prototype.onshow+=";this.hideselectboxes(pm,pm.subx,pm.suby,maxw,maxh,pm.lev)"
  makeCM.prototype.hideselectboxes=function(pm,x,y,w,h,l){
    var selx,sely,selw,selh,i
    if(!this.sel){
      this.sel=this.doc.getElementsByTagName("SELECT")
		  this.sel.level=0
    }
    var sel=this.sel
    for(i=0;i<sel.length;i++){
			selx=0; sely=0; var selp;
			if(sel[i].offsetParent){selp=sel[i]; while(selp.offsetParent){selp=selp.offsetParent; selx+=selp.offsetLeft; sely+=selp.offsetTop;}}
			selx+=sel[i].offsetLeft; sely+=sel[i].offsetTop
			selw=sel[i].offsetWidth; selh=sel[i].offsetHeight			
			if(selx+selw>x && selx<x+w && sely+selh>y && sely<y+h){
				if(sel[i].style.visibility!="hidden"){sel[i].level=l; sel[i].style.visibility="hidden"; if(pm){ if(!pm.mout) pm.mout=""; pm.mout+=this.name+".sel["+i+"].style.visibility='visible';"}}
      }else if(l<=sel[i].level && !(pm&&l==0)) sel[i].style.visibility="visible"
    }
  }
}
