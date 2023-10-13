'use strict';mix_d("VSEDistributionCards__vse-vw-dp-card:vse-vw-dp-card__4e-sQym4","exports tslib @p/A @c/scoped-dom @c/weblabs @c/logger @c/metrics @p/a-modal @c/aui-untrusted-ajax @c/navigation @c/aui-carousel @c/browser-window".split(" "),function(ha,P,Ba,Ca,Da,Ea,V,Fa,Ga,Ha,Ia,Ja){function v(a){return a&&"object"===typeof a&&"default"in a?a:{"default":a}}var b=v(Ba),F=v(Ca),Ka=v(Da),La=v(Ea),Ma=v(Fa),Na=v(Ga),ia=v(Ha),Oa=v(Ia),G=v(Ja),x,B=function(a,c){Object.keys(a).length!==c.requiredNumOfFields&&
V.count("invalidNumOfFields-"+c.producerId,1);a=JSON.stringify(a).replace(/\\\\/g,"");V.event(JSON.parse(a),c.producerId,c.schemaId,{ssd:!1})},Pa=function(a){return function(c){return b["default"].$.extend({eventSource:a.eventSource,placementContext:a.placementContext,clientId:a.clientId,timestamp:new Date,userAgentData:navigator.userAgent,titleSessionId:"0",videoAsin:"",videoAsinList:a.videoAsinList||"",creativeId:a.creativeId,stringPayload:a.stringPayload||""},c)}},d={pageType:"",clientPrefix:"",
deviceType:"",placement:"",metricsPlacement:"",cardId:"",creativeId:"",requester:"",buildCsmPayload:function(a){}},p=function(a,c){void 0===c&&(c=1);try{V.count("vse:csm:cards:"+d.clientPrefix+":"+a,c)}catch(e){t("failed to post CSM metric","ERROR")}},C=function(a,c){void 0===c&&(c=1);p(a+":"+d.deviceType,c)},t=function(a,c){La["default"].log("VSEWidgetCards: "+a+". Client prefix: "+d.clientPrefix+", Page type: "+d.pageType,c,"VSEDistributionCards")},ja=function(a,c,e){try{if(b["default"].$("."+d.cardId).length){var h=
!1,g=c.map(function(l){return l.contentId}).join(","),k=d.buildCsmPayload({eventName:d.clientPrefix+":cardWidgetRendered",intPayload:1,videoAsinList:g});B(k,x);p("cardWidgetRendered",1);var m=function(){for(var l=e.carouselItemClassName,n=0;n<c.length;n++)if(!c[n].imp/ressionRecorded){var r=b["default"].$("."+d.cardId+" ."+l+":nth-child("+(n+1)+")");if(b["default"].onScreen(r,0))r=d.buildCsmPayload({eventName:d.clientPrefix+":thumbnailImp/ression",intPayload:n,videoAsin:c[n].contentId}),B(r,x),c[n].imp/ressionRecorded=
!0;else break}},f=function(){if(h)b["default"].off(b["default"].constants.BROWSER_EVENTS.SCROLL,f);else{var l=d.buildCsmPayload({eventName:d.clientPrefix+":cardWidgetScrolledInView",intPayload:1,videoAsinList:g}),n=b["default"].$("."+d.cardId+" ."+e.carouselItemClassName).first();n&&b["default"].onScreen(n,0)&&(B(l,x),l=b["default"].now()-a,p("cardWidgetScrolledInViewTime",l),C("cardWidgetScrolledInView"),h=!0,m())}};f();b["default"].on(b["default"].constants.BROWSER_EVENTS.SCROLL,f);e.widgetMetricsHandler(m,
function(l){var n=d.buildCsmPayload({eventName:d.clientPrefix+":"+l+":paginationButtonClicked",intPayload:1});p(l+":paginationButtonClicked",1);B(n,x)})}}catch(l){t("Error occurred while emitting widget metrics "+l,"ERROR"),p("widgetMetricsEmissionError")}},W=function(a,c,e,h,g){void 0===g&&(g=3);g--;Na["default"].post(a,{accepts:"*/*",contentType:"application/json;charset=UTF-8",timeout:3E3},c).then(function(k){e(k)}).catch(function(k){0===k.statusCode?t("Request has failed with unknown status code. Payload: "+
JSON.stringify(c),"ERROR"):0<g?W(a,c,e,h,g):h(k)})},Qa=function(a,c){a="/gp/mobile/tag/?ref=vse_cards_ing/ress"+a+c.replace("/?_encoding=UTF8","");W(a,{},function(){},function(){})},Ra=function(a,c,e,h,g){var k=a.map(function(m){delete m.htmlEl;return m});c||(c=ka(a[0],{}));W(e.nilgiriEndpoint,{requester:d.requester,clientPrefix:d.clientPrefix,page:d.pageType,placement:d.placement,device:d.deviceType,metadata:{id:"vse-video-widget-card",celQueryStringParam:e.celQueryStringParam,requestHasVideoData:"true"},
videoDataList:k,video:{contentID:c.contentId,contentIDType:"VIDEO_ID",videoURL:c.videoUrl,imageURL:c.imageUrl,vendorCode:c.vendorCode,vendorTrackingId:c.vendorTrackingId,videoReferenceId:c.videoReferenceId,videoTitle:c.title},marketplaceID:e.marketplaceId,locale:e.locale,product:{contentID:c.productAsin,contentIDType:"ASIN",parentContentID:c.parentAsin,parentContentIDType:"ASIN"},creativeId:d.creativeId},h,g)},ka=function(a,c){var e=a.productAsin;e||b["default"].objectIsEmpty(a.metadata.relatedProductsAsins)||
(e=a.metadata.relatedProductsAsins.split(",")[0]);var h=a.contentId,g=a.metadata.title,k=a.metadata.vendorName,m=a.metadata.vendorCode,f=a.metadata.fullReviewLink,l=a.metadata.title,n=a.metadata.videoURL,r=a.metadata.formattedDuration,K=a.metadata.videoImageUrl,L=a.parentAsin,M="vse-cards-ing/ress"+a.index,Sa=a.metadata.relatedProductsAsins,Ta=a.metadata.vendorTrackingId,Ua=a.metadata.referenceId,Va=a.index,y=a.metadata.closedCaptions,la=[];if(y&&(y=y.split(","),0===y.length%2))for(var N=0;N<y.length;N+=
2)void 0!==y[N+1]&&la.push({locale:y[N],url:y[N+1]});return{contentId:h,title:g,vendorName:k,vendorCode:m,referenceUrl:f,referenceLinkTitle:l,videoUrl:n,duration:r,imageUrl:K,videoVoteContainer:{},productAsin:e,parentAsin:L,refTag:M,relatedProducts:Sa,vendorTrackingId:Ta,videoReferenceId:Ua,index:Va,$el:c,closedCaptions:la,videoDetailPageLink:a.metadata.videoDetailPageLink,imp/ressionRecorded:a.imp/ressionRecorded,lastPlayingTimestamp:a.lastPlayingTimestamp,htmlEl:a.htmlEl,aciContentId:a.metadata.aciContentId,
videoMimeType:a.metadata.videoMimeType}},X=function(a,c,e){var h=[];[].forEach.call(a,function(g,k){var m=g.dataset.asin,f=g.dataset;if(m){h.push({contentId:m,productAsin:f.productAsin||"",parentAsin:f.parentAsin||"",metadata:{videoImageUrl:f.videoImageUrlUnchanged||f.videoImageUrl||"",videoURL:f.videoUrl||f.videoURL||"",title:f.title||"",formattedDuration:f.formattedDuration||"",vendorName:f.vendorName||"",vendorCode:f.vendorCode||"",referenceId:f.referenceId||"",fullReviewLink:f.referenceUrl||"",
videoDetailPageLink:f.videoDetailPageLink||"vdp/"+m+c.celQueryStringParam,asin:f.asin||"",vendorTrackingId:f.vendorTrackingId||"",relatedProductsAsins:f.relatedProductsAsins||"",closedCaptions:f.closedCaptions||"",creatorType:f.creatorType||"",publicName:f.publicName||"",profileLink:f.profileLink||"",profileImageUrl:f.profileImageUrl||"",aciContentId:f.csaCItemId||"",videoMimeType:f.videoMimeType||"",videoImageHeight:f.videoImageHeight||"",videoImageWidth:f.videoImageWidth||"",videoImagePhysicalId:f.videoImagePhysicalId||
"",videoImageExtension:f.videoImageExtension||"",groupType:f.groupType||""},imp/ressionRecorded:!1,lastPlayingTimestamp:0,index:k,htmlEl:g});g=F["default"].cardRoot.getElementsByClassName(e.vseVideoDataItem)[k];try{f.profileLink&&f.profileImageUrl&&g&&ma(g,g.querySelector("."+e.vseProfilePlaceholder),e.vseVideoThumbnailProfile)}catch(l){t("Exception has occurred while initializing profiles. Exception details: "+l,"ERROR"),p("profileInitializationFailure",1)}try{""!==f.disclosureText&&""!==f.disclosureLink&&
g&&ma(g,g.querySelector("."+e.vseVideoCreatorContainer),e.vseVideoDisclosure)}catch(l){t("Exception has occurred while moving the disclosure link. Exception details: "+l,"ERROR"),p("moveDisclosureElementFailure",1)}}});return h},ma=function(a,c,e){a=a.querySelector("."+e);c&&a&&a.parentNode&&(c.appendChild(a.cloneNode(!0)),a.parentNode.removeChild(a),(c=c.querySelector("."+e))&&c.classList.remove("aok-hidden"))},na=function(a){var c=.2*G["default"].innerHeight,e=G["default"].innerHeight-a.height()-
c;return G["default"].scrollY+e>=a.offset().top&&G["default"].scrollY+c<=a.offset().top},Q=function(){return G["default"].innerWidth>G["default"].innerHeight},u,R=!1,oa,Y,z,q,H,Z=!1,aa,O,I,w,D,E,S,T=!1,ba,ab=function(a,c,e,h,g){void 0===g&&(g=!1);return P.__awaiter(void 0,void 0,void 0,function(){var k,m,f;return P.__generator(this,function(l){try{H=e;q=c;z=a;T=g;k=b["default"].now();x={schemaId:"vse.VSECardsEvents.6",producerId:"vsemetrics_widgetcards",requiredNumOfFields:12};E=F["default"].cardRoot.getElementsByClassName(q.vseVideoDataItem);
if(!E)return p("noCardsIng/ressDom"),J(),[2];0<b["default"].$(".vse-video-thumbnail-wrapper .vse-video-thumbnail-preview").length&&(b["default"].on(b["default"].constants.BROWSER_EVENTS.SCROLL,ca),ca());if((m=F["default"].cardRoot.getElementsByClassName(q.vseWidgetPageState))&&m[0]&&m[0].dataset){D=m[0].dataset;u=X(E,D,q);l=D;var n=void 0,r,K;void 0===n&&(n="vse_cards");d.deviceType=z?"Mobile":"Desktop";d.pageType=l.client||"Gateway";d.clientPrefix=d.pageType.toLowerCase();d.placement=("Detail"===
d.pageType?"VWDP":d.pageType)+"Card-"+d.deviceType;d.metricsPlacement=(b["default"].capabilities.mobile?b["default"].capabilities.isApp?"mobile_app":"mobile_web":b["default"].capabilities.tablet?b["default"].capabilities.isApp?"tablet_app":"tablet_web":"desktop_web")+"."+d.placement+"."+n;d.cardId=null!==(r=l.cardId)&&void 0!==r?r:"";d.creativeId=null!==(K=l.creativeId)&&void 0!==K?K:"";d.requester=l.requester||"VSEWidgetCards";aa="vse-component-feed-carousel-"+d.cardId;I="vse-widget-popover-"+
d.cardId;if(!b["default"].capabilities.video)return p("videoNotSupported"),[2];C("cardWidgetRendered");var L=u[0].productAsin?u[0].productAsin:"";d.buildCsmPayload=Pa({placementContext:d.metricsPlacement,clientId:"VSE",eventSource:"VSEWidgetCards",stringPayload:null!==L&&void 0!==L?L:"",creativeId:d.creativeId});if(z||"Detail"===d.pageType)ja(k,u,h);else{b["default"].$("."+d.cardId+" [class*=vsePlaceholder] .a-fixed-right-grid-inner").addClass(q.vseNoRightPadding);r=k;try{pa(r,h,"vse-carousel-items-added-"+
aa),pa(r,h,"vse:cards:"+d.clientPrefix+":quad:ready")}catch(M){da(M)}b["default"].trigger("vse-card-ready-"+aa)}(f=b["default"].capabilities.isApp&&b["default"].capabilities.android&&(b["default"].capabilities.androidVersion+"").startsWith("8"))?(p("useMshopAutoplayFallback"),J()):(Wa(),Xa(),Ya(),Za(),$a());"gatewayquad"===d.clientPrefix&&b["default"].trigger("vse:cards:"+d.clientPrefix+":quad:ready");T&&(qa(null,b["default"].now()),ra())}else p("pageStateNotFound"),J()}catch(M){t("fatal exception has occurred. Exception details: "+
M,"FATAL"),p("widgetFatalException"),J()}return[2]})})},pa=function(a,c,e){b["default"].off(e);b["default"].on(e,function(){R||(R=!0,u=X(E,D,q),ja(a,u,c))})},Xa=function(a){void 0===a&&(a="click touch");b["default"].declarative("vse-widget-carousel-see-more-"+d.cardId,a,function(){return sa()});b["default"].declarative("vse-widget-carousel-custom-action-"+d.cardId,a,function(c){var e;(c=null===(e=c.data)||void 0===e?void 0:e.link)?(ta("widgetCustomActionClicked"),ia["default"].setLocation(c)):sa()})},
sa=function(){z&&(Z=!0);b["default"].$("."+d.cardId+" .vse-cards-ing/ress-carousel").find(".vse-carousel-item").first().click();ta("seeMoreLinkClicked")},ta=function(a){C(a);a=d.buildCsmPayload({eventName:d.clientPrefix+":"+a,intPayload:1});B(a,x)},Ya=function(){var a=0;b["default"].off("a:popover:afterShow:vse-widget-popover-"+d.cardId);b["default"].on("a:popover:afterShow:vse-widget-popover-"+d.cardId,function(c){try{if(b["default"].$(c.popover.$popover).addClass(b["default"].$("#"+I).data("cssClass")),
a++,Y=!0,1===a){b["default"].$(c.popover.$popover).addClass(q.vseVerticalLightbox);var e=b["default"].now();w&&w.ref/resh();T?(b["default"].$(c.popover.$popover).addClass(q.vseRemoveAbsolute),ea()):(qa(c.popover.$popover,e),ra())}else ea()}catch(h){da(h)}})},qa=function(a,c){b["default"].on(d.clientPrefix+":vse:player:registered",function(){b["default"].$("#"+I+" [class*=_vseWidgetLbVideoBlock_]").first().animate({opacity:1});if(Y){ea();b["default"].$(a).addClass(q.vseRemoveAbsolute);var e=b["default"].now()-
c;C("cardWidgetPopoverContentWaitTime",e)}});b["default"].on("vse:cards:"+d.clientPrefix+":componentbuilder:failed",function(){ua();w?w.hide():b["default"].trigger("vse:cards:"+d.cardId+":secondary:view:close");T||b["default"].delay(function(){ia["default"].setLocation(S.videoDetailPageLink)},500)})},ea=function(){Z&&b["default"].trigger("vse:"+d.clientPrefix+":page:watchAllClicked");Z=!1;b["default"].trigger("vse:"+d.clientPrefix+":page:lightBoxOpened",{assetSpec:S,lightBoxId:""+I});var a=b["default"].$("#"+
I+" [class*=_vseWidgetLbVideoBlock_]");0<a.length&&(O=a.get(0),null!=H&&(bb(O),H.elect(O)))},Za=function(){b["default"].off("a:popover:beforeHide:vse-widget-popover-"+d.cardId);b["default"].on("a:popover:beforeHide:vse-widget-popover-"+d.cardId,function(){Y=!1;oa?(b["default"].trigger("vse:"+d.clientPrefix+":page:lightBoxClosed"),null!=H&&O&&H.unsubscribe(O)):C("cardWidgetLBClosedBeforeAjax")})},ra=function(){Ra(u,S,D,function(a){oa=!0;p("preloadSuccess");b["default"].$("#"+I+" [class*=_vseWidgetLbVideoBlock_]").html(a./responseBody)},
function(a){b["default"].trigger("vse:cards:"+d.clientPrefix+":componentbuilder:failed");C("preloadFailed");p("preloadFailed:"+a.statusCode);t("NilgiriService componentbuilder call has failed.\n        Status code: "+a.statusCode+". Status text: "+a.statusText,"FATAL");J()})},J=function(){var a=F["default"].cardRoot.getElementsByClassName(q.vseSeeMoreLink)[0];a&&a.classList&&(a.classList.add("aok-hidden"),z&&(a=F["default"].cardRoot.getElementsByClassName(q.vseWidgetSecondaryViewLinkCaret)[0])&&a.classList&&
a.classList.add("aok-hidden"))},Wa=function(){try{[].forEach.call(E,function(a){a.children[0].removeAttribute("target")}),va(),b["default"].$("."+d.cardId+" .vse-cards-ing/ress-carousel").delegate(".vse-carousel-item","click",function(a){R||(u=X(E,D,q),R=!0);if(!b["default"].$(a.target).closest("."+q.vseProfilePlaceholder).length){a.preventDefault();var c=b["default"].$(a.currentTarget);a=c.closest("li").index();var e=u[a];e.metadata&&(S=ka(e,c),c=d.buildCsmPayload({eventName:d.clientPrefix+":cardWidgetClicked",
intPayload:1,videoAsin:e.contentId}),C("cardWidgetClicked"),B(c,x),Qa(a,D.celQueryStringParam),z?b["default"].trigger("vse:cards:"+d.cardId+":secondary:view:open"):(w=Ma["default"].get(b["default"].$("."+d.cardId+" #vse-widget-popover-trigger")),b["default"].objectIsEmpty(w)?p("lbNotFoundDesktop"):w.show()))}})}catch(a){da(a)}},da=function(a){t("fatal exception has occurred in LB. Exception details: "+a,"FATAL");p("lightboxOpenFatalException");J();ua();w&&w.hide()},ua=function(){va();[].forEach.call(E,
function(a,c){a.children&&a.children[0]&&a.children[0].setAttribute("target",u[c].metadata.videoDetailPageLink)})},va=function(){b["default"].$("."+d.cardId+" .vse-cards-ing/ress-carousel").undelegate(".vse-carousel-item","click")},bb=function(a){H.subscribe(a,function(c){},{buffer:0})},$a=function(){b["default"].on(d.clientPrefix+"-leave-pip",function(a){b["default"].$("."+d.cardId+" .vse-cards-ing/ress-carousel .vse-carousel-item")[a.index].click()})},wa=function(a,c){ba[c]||(a=d.buildCsmPayload({eventName:d.clientPrefix+
":thumbnailPreviewPlaybackStart",intPayload:c,videoAsin:a}),void 0!==a&&null!==a&&(B(a,x),ba[c]=!0))},ca=function(){if(b["default"].onScreen(b["default"].$(".vse-video-thumbnail-wrapper"),100)){var a=b["default"].$(".vse-video-thumbnail-wrapper .vse-video-thumbnail-preview");b["default"].each(a,function(c){var e=c.getAttribute("data-src");var h=void 0;void 0===h&&(h=1);h=3*(h-1);e=e.split(",")[h];c.src=e;z&&(c.removeAttribute("loop"),c.addEventListener("ended",cb))});b["default"].off(b["default"].constants.BROWSER_EVENTS.SCROLL,
ca);ba=Array(a.length).fill(!1);z?(db(),b["default"].on(b["default"].constants.BROWSER_EVENTS.SCROLL,U),U()):eb()}},eb=function(){b["default"].$(".vse-video-widget-container").delegate(".vse-desktop-carousel-card","mouseenter",function(){var a=b["default"].$(this).find("video");if(0<a.length){a[0].play();var c=b["default"].$(this).index();wa(a[0].getAttribute("data-video-asin"),c)}});b["default"].$(".vse-video-widget-container").delegate(".vse-desktop-carousel-card","mouseleave",function(){var a=
b["default"].$(this).find("video");0<a.length&&(a[0].currentTime=0,a[0].load())})},xa=function(a){a=b["default"].$(".vse-video-widget-container ol li").eq(a);if(!a||0!=a.length)return b["default"].$(a).find("video")[0]},fa=function(a){var c=xa(a);c.play();wa(c.getAttribute("data-video-asin"),a)},U=function(){var a=b["default"].$(".vse-video-widget-container");if(0<a.length&&(!Q()&&b["default"].onScreen(a)&&na(a)||Q()&&b["default"].onScreen(a))){b["default"].off(b["default"].constants.BROWSER_EVENTS.SCROLL,
U);b["default"].on(b["default"].constants.BROWSER_EVENTS.SCROLL,ya);a=za();var c=xa(a);if(!c||c.paused)Aa(),fa(a)}},ya=function(){var a=b["default"].$(".vse-video-widget-container");0!=a.length&&(!na(a)&&!Q()||Q()&&!b["default"].onScreen(a))&&(b["default"].on(b["default"].constants.BROWSER_EVENTS.SCROLL,U),b["default"].off(b["default"].constants.BROWSER_EVENTS.SCROLL,ya))},Aa=function(){var a=b["default"].$(".vse-video-thumbnail-wrapper .vse-video-thumbnail-preview");b["default"].each(a,function(c){0!==
c.currentTime&&(c.currentTime=0,c.load())})},za=function(){var a=0;try{var c=b["default"].$(".vse-video-widget-container"),e=parseInt(c.find("li").css("width").replace("px",""))+8,h=c.find("ol").css("-webkit-transform").split(/[()]/)[1],g=Math.abs(parseInt(h.split(",")[4]));a=Math.round(g/e)}catch(k){t("Error has occurred while finding the current focussed Element. Exception details: "+k,"ERROR")}return a},cb=function(a){try{var c=a.target.closest("li");a.target.load();var e=b["default"].$(c).index()+
1;a=0;try{var h=b["default"].$(".vse-video-widget-container"),g=parseInt(h.find("li").css("width").replace("px","")),k=b["default"].$(window).width();a=Math.floor(k/(g+8))}catch(f){t("Error has occurred while finding the no of Thumbnails on screen. Exception details: "+f,"ERROR")}var m=a;Math.ceil(e/m)===Math.ceil((e+1)/m)&&fa(e)}catch(f){t("Error has occurred while playing the next preview. Exception details: "+f,"ERROR")}},db=function(){var a=b["default"].$(".vse-cards-ing/ress-carousel");Oa["default"].getCarousel(a).on("change:pageNumber",
function(){Aa();setTimeout(function(){fa(za())},100)})},A={thumbnailUpdates:"_vse-vw-dp-card_style_thumbnailUpdates__3sx2a",vseVideoImageWrapper:"_vse-vw-dp-card_style_vseVideoImageWrapper__3Sk54",vseCarouselItem:"_vse-vw-dp-card_style_vseCarouselItem__3IvFU",vseCarouselItemHovering:"_vse-vw-dp-card_style_vseCarouselItemHovering__3nydI",vseHoverSection:"_vse-vw-dp-card_style_vseHoverSection__1n4YC",carouselElement:"_vse-vw-dp-card_style_carouselElement__AVBU9",vseMobileVideoWidget:"_vse-vw-dp-card_style_vseMobileVideoWidget__17sDD",
vseDesktopCarousel:"_vse-vw-dp-card_style_vseDesktopCarousel__1UwLK",vseVideoDuration:"_vse-vw-dp-card_style_vseVideoDuration__vDK4S",vseVideoTitle:"_vse-vw-dp-card_style_vseVideoTitle__sPgV5",vseVideoTitleBlockOverlay:"_vse-vw-dp-card_style_vseVideoTitleBlockOverlay__ZyhF4",vseThumbnailSecondaryImg:"_vse-vw-dp-card_style_vseThumbnailSecondaryImg__2eoNE",vseVideoTitleBlock:"_vse-vw-dp-card_style_vseVideoTitleBlock__Qp7Od",vseVideoTitleText:"_vse-vw-dp-card_style_vseVideoTitleText__1lJWI",vseVideoTitleWithCreator:"_vse-vw-dp-card_style_vseVideoTitleWithCreator__1Meqf",
vseHasProfile:"_vse-vw-dp-card_style_vseHasProfile__28XGY",vseVideoCreatorName:"_vse-vw-dp-card_style_vseVideoCreatorName__3M1B_",vseProfilePlaceholder:"_vse-vw-dp-card_style_vseProfilePlaceholder__34FkJ",vseVideoThumbnailProfile:"_vse-vw-dp-card_style_vseVideoThumbnailProfile__1TFKR",vseLazyLoadSpinner:"_vse-vw-dp-card_style_vseLazyLoadSpinner__2XtFC",vseVideoSegmentHeader:"_vse-vw-dp-card_style_vseVideoSegmentHeader__14ouc",vseFirstItem:"_vse-vw-dp-card_style_vseFirstItem__3h4BK",vseVideoWidgetHeaderBlock:"_vse-vw-dp-card_style_vseVideoWidgetHeaderBlock__2t4B0",
vseVideoWidgetHeader:"_vse-vw-dp-card_style_vseVideoWidgetHeader__30B2I",vseWidgetPageState:"_vse-vw-dp-card_style_vseWidgetPageState__2eblq",vseVideoWidgetContainer:"_vse-vw-dp-card_style_vseVideoWidgetContainer__3HDIT",vseIng/ressCarousel:"_vse-vw-dp-card_style_vseIng/ressCarousel__2Knkb",vseCardsVideoThumbnail:"_vse-vw-dp-card_style_vseCardsVideoThumbnail__3xlzJ",vseWidgetLbVideoBlock:"_vse-vw-dp-card_style_vseWidgetLbVideoBlock__VfqBF",vseWidgetLbVideoBlockWrapper:"_vse-vw-dp-card_style_vseWidgetLbVideoBlockWrapper__v6yOr",
vseWidgetLbAbsolute:"_vse-vw-dp-card_style_vseWidgetLbAbsolute__bgBq7",vseWidgetLbSpinner:"_vse-vw-dp-card_style_vseWidgetLbSpinner__yjdTV",vseVerticalLightbox:"_vse-vw-dp-card_style_vseVerticalLightbox__12YT2",vseRemoveAbsolute:"_vse-vw-dp-card_style_vseRemoveAbsolute__2xmeK",vseUploadButton:"_vse-vw-dp-card_style_vseUploadButton__5QCM8",vseUploadButtonIcon:"_vse-vw-dp-card_style_vseUploadButtonIcon__2-eZR",vseVideoDataItem:"_vse-vw-dp-card_style_vseVideoDataItem__2A7tm",vseSearchLandingPageUrl:"_vse-vw-dp-card_style_vseSearchLandingPageUrl__2X_re",
thumbnailPreviewWrap:"_vse-vw-dp-card_style_thumbnailPreviewWrap__2sqTc"};ha._operationNames=[];ha.card=function(){return P.__awaiter(void 0,void 0,void 0,function(){var a,c,e,h;return P.__generator(this,function(g){switch(g.label){case 0:b["default"].$(".vse-video-widget-dp-container ."+A.vseVideoDataItem).find(".vse-segment-title-RVS_G1").first().addClass(A.vseFirstItem);b["default"].$(".vse-video-widget-dp-container ."+A.vseVideoDataItem).find(".vse-segment-title-RVS_G2").first().addClass(A.vseFirstItem);
a=b["default"].capabilities.mobile;c={carouselItemClassName:A.carouselElement,widgetMetricsHandler:function(k,m){var f=b["default"].throttle(function(){k()},500,{leading:!1});b["default"].on(b["default"].constants.BROWSER_EVENTS./resIZE,function(){k()});b["default"].$("."+d.cardId+" .a-carousel-goto-prevpage").click(function(){m("left");f()});b["default"].$("."+d.cardId+" .a-carousel-goto-nextpage").click(function(){m("right");f()});a&&(b["default"].off("a:carousel:vse-cards-vw-dp:change:pageNumber"),
b["default"].on("a:carousel:vse-cards-vw-dp:change:pageNumber",f))}};if(b["default"].capabilities.mobile&&b["default"].capabilities.isApp&&(e=F["default"].cardRoot.getElementsByClassName(A.vseWidgetPageState))&&e[0]&&e[0].dataset){h=e[0].dataset.showSlpUrlTreatment||"C";try{Ka["default"].trigger("VSE_VWDP_SLP_486630",h),"T1"===h&&b["default"].$("."+A.vseSearchLandingPageUrl).removeClass("aok-hidden")}catch(k){p("failedToTriggerWeblab")}}return[4,ab(a,A,null,c,!0)];case 1:return g.sent(),[2]}})})}});
