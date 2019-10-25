(function ($, window, layer) {
    $.extend({
        websocket: function (config) {
            var conf = $.extend({
                url: "",
                onopen: function () {

                },
                onerror: function () {

                },
                onmessage: function (msg) {

                },
                onclose: function () {

                }
            }, $.isBlank(config) ? {} : config);
            var websocket = null;
// 判断当前浏览器是否支持WebSocket
            var agree = "ws";
            if (0 <= window.location.protocol.indexOf("https")) {
                agree = "wss";
            }
            var url = agree + "://" + conf.url;
            if ('WebSocket' in window) {
                websocket = new WebSocket(url);
            } else if ('MozWebSocket' in window) {
                websocket = new MozWebSocket(url);
            } else {
                console.log('Not support websocket')
            }

// 连接发生错误的回调方法
            websocket.onerror = function () {
                console.log("WebSocket error");
                conf.onerror();
            };

// 连接成功建立的回调方法
            websocket.onopen = function () {
                console.log("WebSocket connect to [ " + url + " ]");
                conf.onopen();
            };

// 接收到消息的回调方法
            websocket.onmessage = function (event) {
                console.log(event);
                var action = null;
                var level = null;
                var msg = null;
                try {
                    var data = JSON.parse(event.data);
                    action = data.action;
                    level = data.level;
                    msg = data.msg;
                } catch (err) {
                    action = "ALERT";
                    level = "ERROR";
                    msg = event;
                }
                switch (action) {
                    case "ALERT":
                        layer.alert(msg);
                        break;
                    case "MSG":
                        var num = $(".msg-num-total").eq(0).text();
                        $(".msg-num-total").text(parseInt(num) + 1);
                        switch (level) {
                            case "SUCCESS":
                                num = $(".msg-num-success").eq(0).text();
                                $(".msg-num-success").text(parseInt(num) + 1);
                                break;
                            case "INFO":
                                num = $(".msg-num-info").eq(0).text();
                                $(".msg-num-info").text(parseInt(num) + 1);
                                break;
                            case "WARNING":
                                num = $(".msg-num-warning").eq(0).text();
                                $(".msg-num-warning").text(parseInt(num) + 1);
                                break;
                            case "ERROR":
                                num = $(".msg-num-danger").eq(0).text();
                                $(".msg-num-danger").text(parseInt(num) + 1);
                                break;
                        }
                        break;
                }
                conf.onmessage(event);
            };

// 连接关闭的回调方法
            websocket.onclose = function () {
                console.log("WebSocket close");
                conf.onclose();
            };

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
            window.onbeforeunload = function () {
                websocket.close();
            };

// 发送消息
            this.sendMsg = function (message) {
                websocket.send(message);
            }
        }
    });
})(jQuery, window, layer);