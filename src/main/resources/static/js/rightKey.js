// // 文档加载后激活函数
// $(document).ready(function() {
//
// 	// $(".list-group-item").contextmenu(function(e) {
// 	//
// 	// }
// 	// 鼠标右键事件
// 	$(document).contextmenu(function(e) {
// 		// 获取窗口尺寸
// 		var winWidth = $(document).width();
// 		var winHeight = $(document).height();
// 		// 鼠标点击位置坐标
// 		var mouseX = e.pageX;
// 		var mouseY = e.pageY;
// 		// ul标签的宽高
// 		var menuWidth = $(".contextmenu").width();
// 		var menuHeight = $(".contextmenu").height();
// 		// 最小边缘margin(具体窗口边缘最小的距离)
// 		var minEdgeMargin = 10;
// 		// 以下判断用于检测ul标签出现的地方是否超出窗口范围
// 		// 第一种情况：右下角超出窗口
// 		if(mouseX + menuWidth + minEdgeMargin >= winWidth &&
// 			mouseY + menuHeight + minEdgeMargin >= winHeight) {
// 			menuLeft = mouseX - menuWidth - minEdgeMargin + "px";
// 			menuTop = mouseY - menuHeight - minEdgeMargin + "px";
// 		}
// 		// 第二种情况：右边超出窗口
// 		else if(mouseX + menuWidth + minEdgeMargin >= winWidth) {
// 			menuLeft = mouseX - menuWidth - minEdgeMargin + "px";
// 			menuTop = mouseY + minEdgeMargin + "px";
// 		}
// 		// 第三种情况：下边超出窗口
// 		else if(mouseY + menuHeight + minEdgeMargin >= winHeight) {
// 			menuLeft = mouseX + minEdgeMargin + "px";
// 			menuTop = mouseY - menuHeight - minEdgeMargin + "px";
// 		}
// 		// 其他情况：未超出窗口
// 		else {
// 			menuLeft = mouseX + minEdgeMargin + "px";
// 			menuTop = mouseY + minEdgeMargin + "px";
// 		};
// 		// ul菜单出现
// 		$(".contextmenu").css({
// 			"left": menuLeft,
// 			"top": menuTop
// 		}).show();
// 		// 阻止浏览器默认的右键菜单事件
// 		return false;
// 	});
// 	// 点击之后，右键菜单隐藏
// 	$(document).click(function() {
// 		$(".contextmenu").hide();
// 	});
// });