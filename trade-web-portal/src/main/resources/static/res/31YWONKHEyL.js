'use strict';mix_d("PostsDPSameBrandMobileCards__posts-dp-same-brand-desktop-card:posts-dp-same-brand-desktop-card__8GrpjsIz","exports tslib @c/browser-operations @c/logger @c/pagemarker @c/dom @c/navigation @c/remote-operations @c/tracking @c/metrics @c/aui-utils @c/browser-window".split(" "),function(A,m,v,B,N,O,P,Q,C,w,R,S){function n(a){return a&&"object"===typeof a&&"default"in a?a:{"default":a}}var D=n(v),T=n(B),U=n(N),q=n(O),x=n(P);v=n(Q);var V=n(w),W=n(R),E=n(S),y;(y||(y={})).followButtonClickHandler=
"followButtonClickHandler";var F=v["default"].setup(),X=function(a,d,b){C.addTracking(F.follow)({bfid:a}).then(function(b){return G("AddFavorite",a,"bft_dpp",b)}).then(d).catch(b)},Y=function(a,d,b){C.addTracking(F.unfollow)({bfid:a}).then(function(b){return G("RemoveFavorite",a,"bft_dpp",b)}).then(d).catch(b)},G=function(a,d,b,f){a=m.__assign({eventType:a,entityType:"brand",entityId:d,reftag:b},f);V["default"].event(a,"brandfollow","PIM.FollowEvent.7");return Promise./resolve("success")},Z=function(a){var d;
if(a=a.event){var b=a.target;b&&("true"!==b.getAttribute("data-issignedin")?(a=null!==(d=b.getAttribute("data-signinurl"))&&void 0!==d?d:"",x["default"].setLocation(a.replace("openid.return_to=","openid.return_to="+x["default"].getLocation().href))):(d=b.getAttribute("data-followbfid"),"true"===b.getAttribute("data-isfollowing")?Y(d,function(){var a=b.getAttribute("data-followtext"),c=b.getAttribute("data-arialabelfollow");b.innerText=a;b.setAttribute("data-isfollowing","false");b.setAttribute("aria-label",
c)},function(){}):X(d,function(){var a=b.getAttribute("data-followingtext"),c=b.getAttribute("data-arialabelfollowing");b.innerText=a;b.setAttribute("data-isfollowing","true");b.setAttribute("aria-label",c)},function(){})))}},H={PageIng/ress:1,PageEg/ress:1,PostImp/ression:2,PostPartialImp/ression:1,PostView:2,PostOffScreen:1,PostClick:1,EndOfFeed:1,BackToTop:1,LoadMore:1,ShowMore:1,WidgetClick:1},p;(function(a){a.PageIng/ress="PageIng/ress";a.PageEg/ress="PageEg/ress";a.PostImp/ression="PostImp/ression";a.PostPartialImp/ression=
"PostPartialImp/ression";a.PostView="PostView";a.PostOffScreen="PostOffScreen";a.PostClick="PostClick";a.EndOfFeed="EndOfFeed";a.BackToTop="BackToTop";a.LoadMore="LoadMore";a.ShowMore="ShowMore";a.WidgetClick="WidgetClick"})(p||(p={}));var I;(I||(I={})).REFERRING_URL="ReferringUrl";var J;(function(a){a.APP_BUILD="appBuild";a.APP_MODE="appMode";a.LANGUAGE="language";a.MARKETPLACE_ID="marketplaceId";a.PAGE_ID="pageId";a.PAGE_TYPE="pageType";a.PLATFORM="platform";a.REQUEST_ID="requestId";a.SESSION_ID=
"sessionId";a.STAGE="stage"})(J||(J={}));var g;(function(a){a.APP_BUILD="app_build";a.APP_MODE="app_mode";a.BROWSING_INSTANCE="browsing_instance";a.CLICK_ACTION="click_action";a.CLICK_TARGET="click_target";a.DESTINATION_TYPE="destination_type";a.DESTINATION_ID="destination_id";a.DWELL_TIME="dwell_time";a.DWELL_TIME_MS="dwell_time_ms";a.ELEMENT_ID="element_id";a.ELEMENT_TYPE="element_type";a.ELEMENT_PX="element_px";a.EVENT_TYPE="event_type";a.EVENT_VERSION="event_version";a.FORWARD_TRANSITION="forward_transition";
a.MOBILE_BUID="mshop_build";a.PAGE_ID="page_id";a.PAGE_TYPE="page_type";a.POST_ID="post_id";a.RANK="rank";a.REASON="reason";a.REQUEST_ID="request_id";a.REQUEST_INSTANCE="request_instance";a.SCROLL_DEPTH_PX="scroll_depth_px";a.SCROLL_DEPTH_RANK="scroll_depth_rank";a.SEQUENCE="sequence";a.SOURCE_TYPE="source_type";a.SOURCE_ID="source_id";a.TIMESTAMP="timestamp";a.ORIGIN_PX="origin_px";a.PLATFORM="platform"})(g||(g={}));var aa=["messageId","timestamp"],ba=function(a){var d=function(){},b=function(){var b=
function(){if(a.XDomainRequest){var b=new a.XDomainRequest;b.onerror=d;b.ontimeout=d;b.onprog/ress=d;b.onload=d;b.timeout=0;return b}if(a.XMLHttpRequest){b=new a.XMLHttpRequest;if(!("withCredentials"in b))throw"";return b}return null};return{isSupported:!0,send:function(a,c){if(c&&a){var e=b();if(!e)throw"";e.open("POST",a,!0);e.setRequestHeader&&e.setRequestHeader("Content-type","text/plain");e.send(c)}}}}(),f=function(){return{isSupported:!!a.navigator.sendBeacon,send:function(b,e){if(b&&e&&!a.navigator.sendBeacon(b,
e))throw"";}}}();return{ajaxClient:b,beaconClient:f}},da=function(){return function(a,d){var b=this;this.flush=function(a){void 0===a&&(a=!1);b.eventsQueue.length&&(a||!1!==b.browserWindow.navigator.onLine)&&(a=b.eventsQueue.length,b.sendWithPriority(b.transportationClient.beaconClient,b.transportationClient.ajaxClient),b./resetBatch(),b.ueCount("EventsSent",a))};this.processEvent=function(a,e){void 0===e&&(e={n:1});var c=b.unloadHandlerCalled=!1;b.isValidAPICall(a)&&(c=b.addEvent(a,e));c?b.ueCount("ClientEventsSuccess"):
b.ueCount("ClientEventsFailed");return c};this.getEventQueueCount=function(){return b.eventsQueue.length};this.buildComp/ressor=function(){var a=function(a){return a.length>("#"+b.dictionaryIndex).length||"#"===a.charAt(0)},e=function(b){if(b)switch(typeof b){case "number":return!(isNaN(b)||Infinity===b)&&a(b.toString());case "boolean":break;case "string":return a(b);default:return!0}return!1},f=function(a){b.eventsDictionary[a]="#"+b.dictionaryIndex++;b.reverseEventsDictionary[b.eventsDictionary[a]]=
a;return b.eventsDictionary[a]},d=function(a){if(e(a)){var c=0;a instanceof Array?c=2:a instanceof Function?c=null:a instanceof Object&&(c=1);switch(c){case 0:return b.eventsDictionary[a]?b.eventsDictionary[a]:f(a);case 2:var h=[];for(c=0;c<a.length;c++)h[c]=d(a[c]);return h;case 1:c={};for(h in a)Object.prototype.hasOwnProperty.call(a,h)&&(c[b.eventsDictionary[h]?b.eventsDictionary[h]:f(h)]=-1===aa.indexOf(h)?d(a[h]):a[h]);return c}}return a};return{buildPOSTObject:function(){return b.stringify({cs:{dct:b.reverseEventsDictionary},
events:b.eventsQueue})},comp/ressData:d}};this.onBeforeUnload=function(){!0!==b.unloadHandlerCalled&&(b.flush(),b.unloadHandlerCalled=!0)};this.stringify=function(a){try{return JSON.stringify(a)}catch(e){return null}};this./resetBatch=function(){b.eventsQueue=[];b.eventsDictionary={};b.reverseEventsDictionary={};b.dictionaryIndex=0;b.batchSize=0;b.lowPriorityTimer=0;b.highPriorityTimer=0};this.sendWithClient=function(a){if(null==b.endpoint||""===b.endpoint)b.ueCount("SushiClientEndpointMissing");else{if(a.isSupported){var c=
b.comp/ressor.buildPOSTObject();return a.send(b.endpoint,c)}throw"";}};this.sendWithPriority=function(){for(var a=[],e=0;e<arguments.length;e++)a[e]=arguments[e];for(e=0;e<a.length;e++){var f=a[e];try{return b.sendWithClient(f)}catch(l){}}return null};this.startHighPriorityQueueTimer=function(){0===b.highPriorityFlushInterval?b.flush():b.highPriorityTimer||(b.highPriorityTimer=setTimeout(b.flush,b.highPriorityFlushInterval,b,!1))};this.startLowPriorityQueueTimer=function(){b.lowPriorityTimer||(b.lowPriorityTimer=
setTimeout(b.flush,b.lowPriorityFlushInterval,b,!1))};this.generateMessageId=function(a,e){return a+"-"+e+"-"+b.eventSequence};this.getCurrentISODate=function(){var a=new Date,b=function(a){return 10>a?"0"+a:a};return"function"===typeof Date.prototype.toISOString?a.toISOString():a.getUTCFullYear()+"-"+b(a.getUTCMonth()+1)+"-"+b(a.getUTCDate())+"T"+b(a.getUTCHours())+":"+b(a.getUTCMinutes())+":"+b(a.getUTCSeconds())+"."+String((a.getUTCMilliseconds()/1E3).toFixed(3)).slice(2,5)+"Z"};this.decorateEvent=
function(a){a.timestamp=b.getCurrentISODate();a.sequence=b.eventSequence;a.messageId=b.generateMessageId(a.request_id,Date.now?Date.now():(new Date).getTime())};this.startTimers=function(a){a.n?b.startHighPriorityQueueTimer():b.startLowPriorityQueueTimer()};this.encodeEvent=function(a,e,f){e=e.length;(499===b.eventsQueue.length||524288<b.batchSize+e)&&b.flush(!0);b.batchSize+=e;a={data:b.comp/ressor.comp/ressData(a)};b.eventsQueue.push(a);b.startTimers(f)};this.addEvent=function(a,e){b.decorateEvent(a);
var c=b.stringify(a);return c?(b.encodeEvent(a,c,e),!0):!1};this.isValidAPIParam=function(a,b){return a&&-1<a.constructor.toString().indexOf(b)};this.isMaxCallsReached=function(){b.eventSequence++;1E3<b.eventSequence&&b.ueCount("SushiClientMaxCallsReached");return 1E3<b.eventSequence};this.isValidAPICall=function(a){return!b.isMaxCallsReached()&&b.isValidAPIParam(a,"Object")};this.ueCount=function(a,e){try{var c=0===e?0:e||(w.count(b.metricsTag+":"+a)||0)+1;w.count(b.metricsTag+":"+a,c);return c}catch(l){return B.log("SushiClient.ueCount error: "+
l,"ERROR"),0}};this.browserWindow=window;this.endpoint=a;this.metricsTag=d;this.eventsQueue=[];this.eventsDictionary={};this.reverseEventsDictionary={};this.eventSequence=this.lowPriorityTimer=this.highPriorityTimer=this.dictionaryIndex=this.batchSize=0;this.unloadHandlerCalled=!1;this.highPriorityFlushInterval=1E3;this.lowPriorityFlushInterval=1E4;this.transportationClient=ba(this.browserWindow);this.comp/ressor=this.buildComp/ressor();this.browserWindow.app||(this.browserWindow.app={});var f=this.browserWindow.app.willDisappear;
this.browserWindow.app.willDisappear=function(){f&&f();b.onBeforeUnload()}}}(),ea=function(){return function(a,d){var b=this;this.reportPageIng/ress=function(){var a=Date.now();b.processEvent(p.PageIng/ress,a)};this.reportPageEg/ress=function(a,c){b.processEvent(p.PageEg/ress,a,c)};this.reportPostClick=function(a,c){b.processEvent(p.PostClick,a,c)};this.reportFollowClick=function(a,c){b.processEvent(p.WidgetClick,a,c)};this.reportShowMoreClick=function(a){b.processEvent(p.ShowMore,a)};this.reportPostView=
function(a,c,e){var d;c=(d={},d[g.DWELL_TIME_MS]=c,d);b.processEvent(p.PostView,a,e,c)};this.reportPostImp/ression=function(a,c){b.processEvent(p.PostImp/ression,a,c)};this.flush=function(){b.sushiClient.flush(!0)};this.buildCommonPostMetricsPayload=function(a){var c,e,d,f=b.metricsSequenceNumber++;return c={},c[g.REQUEST_ID]=b.defaultMetricsProperties.requestId,c[g.REQUEST_INSTANCE]=b.requestInstance,c[g.BROWSING_INSTANCE]=b.browsingInstance,c[g.SEQUENCE]=f,c[g.PAGE_TYPE]=b.defaultMetricsProperties.pageType,
c[g.PAGE_ID]=b.defaultMetricsProperties.pageId,c[g.APP_MODE]=null!==(e=b.defaultMetricsProperties.appMode)&&void 0!==e?e:"prod",c[g.APP_BUILD]=null!==(d=b.defaultMetricsProperties.appBuild)&&void 0!==d?d:"1.0",c[g.EVENT_VERSION]=H[a],c[g.PLATFORM]=b.defaultMetricsProperties.platform,c};this.processEvent=function(a,c,e,d){var f=b.buildCommonPostMetricsPayload(a);e=m.__assign(m.__assign(m.__assign({},f),e),d);e[g.EVENT_VERSION]=H[a];e[g.EVENT_TYPE]=a;e[g.TIMESTAMP]=c;b.sushiClient.processEvent(e)};
this.generateUUID=function(){return"xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,function(a){var b=16*Math.random()|0;return("x"==a?b:b&3|8).toString(16)})};this.metricsSequenceNumber=0;this.sushiClient=new da(d,"SameBrandClientMetrics");this.defaultMetricsProperties=a;this.requestInstance=Math.round(Math.floor(999999*Math.random())+1);this.browsingInstance=this.generateUUID()}}(),K=function(a,d){var b,f=(b={},b[g.POST_ID]=a.postMetricsProperties.postId,b[g.RANK]=a.postMetricsProperties.rank,
b);a=a.startTimeInMs;b=Math.min(36E5,Date.now()-a);d.reportPostView(a,b,f)},L=function(a,d,b){a=a.dataset.postId;if(b.has(a)){var f=b.get(a);K(f,d);b.delete(a)}},M=function(a,d,b,f){b.reportPageEg/ress(a,d);var c;try{for(var e=m.__values(f.values()),g=e.next();!g.done;g=e.next()){var l=g.value;K(l,b);f.delete(l.postMetricsProperties.postId)}}catch(ca){var k={error:ca}}finally{try{g&&!g.done&&(c=e.return)&&c.call(e)}finally{if(k)throw k.error;}}f.clear();b.flush()},fa=function(a){a=x["default"].getLocation();
null==a||null==a.hostname?a="Beta":(a=a.hostname,a="localhost"===a||a.includes("aka..com")||a.includes("corp..com")||a.includes("integ..com")||a.includes("beta..com")||"development..com"===a||/^[a-z]{2}-development\.\.com$/.test(a)?"Beta":/^pre-prod\.\.(com|[a-z]{2})$/.test(a)||/^pre-prod\.\.com?\.[a-z]{2}$/.test(a)||/^[a-z]{2}-pre-prod\.\.com$/.test(a)?"Gamma":"/res"===a?"Prod":"Beta");"Beta"===a&&(a="Gamma");return"https://unagi-na..com/1/events/com..eel.ACEPostMetrics."+
a+".ClientEvents"},r;(r||(r={})).carouselTransitionHandler="carouselTransitionHandler";var ha=function(a,d,b){a.forEach(function(a){var c=JSON.parse(a.dataset.postMetricsProperties);(a=a.querySelector("a"))&&a.addEventListener("click",function(){var a,f,l=Date.now(),k=(a={},a[g.CLICK_TARGET]=c.clickTarget,a[g.POST_ID]=c.postId,a[g.ELEMENT_ID]=c.elementId,a[g.ELEMENT_TYPE]=c.elementType,a[g.RANK]=c.rank,a);d.reportPostClick(l,k);a=(f={},f[g.CLICK_TARGET]=c.clickTarget,f[g.DESTINATION_TYPE]=c.destinationType,
f[g.POST_ID]=c.postId,f[g.RANK]=c.rank,f);M(l,a,d,b)},!0)})},t=function(a){a=a.getBoundingClientRect();return{top:a.top,right:a.right,bottom:a.bottom,left:a.left,width:a.width,height:a.height,x:a.x,y:a.y}},z=function(a){return a&&!Object.values(t(a)).every(function(a){return null===a||0===a||void 0===a})},ia=function(a){if(z(a)){a=t(a);var d=a.y+.5*a.height;return 0<=a.top&&d<=E["default"].innerHeight||0>=a.top&&0<=d}return!1},ja=function(a,d,b){0<b.size&&a.forEach(function(a){a=a.querySelectorAll('[data-type="postElement"]')[0];
L(a,d,b)})},u=function(a,d,b){var f=a.getElementsByClassName("a-carousel-viewport")[0];a=Array.prototype.slice.call(a.getElementsByClassName("a-carousel-card"));ia(f)?a.forEach(function(a){var c=a.querySelectorAll('[data-type="postElement"]')[0];if(z(f)&&z(a)&&"hidden"!==a.style.visibility){var h=t(f);a=t(a);var l=a.left+.5*a.width;h=a.left>=Math.max(h.left,0)&&l<=Math.min(h.right,E["default"].innerWidth)}else h=!1;if(h){var k;b.has(c.dataset.postId)||(c=JSON.parse(c.dataset.postMetricsProperties),
h=(k={},k[g.POST_ID]=c.postId,k[g.RANK]=c.rank,k),d.reportPostImp/ression(Date.now(),h),k={startTimeInMs:Date.now(),postMetricsProperties:c},b.set(c.postId,k))}else L(c,d,b)}):ja(a,d,b)},ka=function(a,d,b){var f=D["default"].setup().define,c=a.querySelectorAll('[data-type="postElement"]');ha(Array.prototype.slice.call(c),d,b);f(r.carouselTransitionHandler,"transitionend",function(){u(a,d,b)});f(r.carouselTransitionHandler,"transitioncancel",function(){u(a,d,b)});c=W["default"].debounce(function(){u(a,
d,b)},200);f("sameBrandDesktopScroll","scroll",c);f("sameBrandDesktop/resize","/resize",c)},la=function(){return m.__awaiter(void 0,void 0,void 0,function(){var a,d,b,f,c,e;return m.__generator(this,function(h){a=q["default"].cardRoot.getElementsByClassName("_posts-dp-same-brand-desktop-card_style_post-carousel-container__1-a11")[0];if(!a||!q["default"].cardRoot.dataset.properties)return[2];d=JSON.parse(q["default"].cardRoot.dataset.properties);b=new ea(d,fa(d.marketplaceId));f=new Map;u(a,b,f);ka(a,
b,f);c=["_posts-dp-same-brand-desktop-card_style_post-carousel-brand-logo-container__2l11B","_posts-dp-same-brand-desktop-card_style_post-carousel-brand-name-container__1tpRW"];c.forEach(function(a){q["default"].cardRoot.getElementsByClassName(a)[0].addEventListener("click",function(){var a=b,c=f,d,e=Date.now(),h=(d={},d[g.CLICK_TARGET]="Header",d[g.DESTINATION_TYPE]="Store",d);M(e,h,a,c)},!0)});e=q["default"].cardRoot.getElementsByClassName("_posts-dp-same-brand-desktop-card_style_brand-follow-button__GQPgx")[0];
e.addEventListener("click",function(a){var c=a.target.getAttribute("data-isfollowing");a=b;var d,e=Date.now();c=(d={},d[g.CLICK_TARGET]="FollowButton",d[g.CLICK_ACTION]="true"===c?"Unfollow":"Follow",d);a.reportFollowClick(e,c)},!0);return[2]})})};A._operationNames=["follow","unfollow"];A.card=function(){return m.__awaiter(void 0,void 0,void 0,function(){var a;return m.__generator(this,function(d){a=D["default"].setup().define;U["default"].pageReady.then(la).catch(function(a){T["default"].log("Failed to /resolve all DOM elements of SameBrandDesktopWidget. Failure reason: "+
a,"ERROR")});a(y.followButtonClickHandler,"click",Z);return[2]})})}});