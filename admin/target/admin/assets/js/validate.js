(function ($, undefined) {
    $.extend({
        //校验
        validate: function (conf) {
            var _this = this;
            conf = $.extend({
                selector: null,
                error: function (element) {
                },
                success: function (element) {
                },
                type: {},
                tag: "validate"
            }, conf);
            var type = $.extend({
                required: function (val) {
                    return !$.isBlank(val);
                }
            }, conf.type);
            if ($.isBlank(conf.selector))
                return;
            this.isOk = function (element) {
                var cl = element.attr(conf.tag);
                if ($.isBlank(cl))
                    return true;
                var cls = cl.split(/\||\&|\(|\)/);
                for (var i in cls) {
                    if ($.isBlank(cls[i]))
                        continue;
                    var vas = true;
                    for (var key in type) {
                        if (0 == cls[i].indexOf(key)) {
                            vas = "type[\"" + key + "\"](\"" + element.val() + "\", \"" + cls[i].replace(key, "") + "\")";
                            break;
                        }
                    }
                    cl = cl.replace(cls[i], vas);
                }
                var ok = eval(cl);
                if (ok) {
                    conf.success(element);
                } else {
                    conf.error(element);
                }
                return ok;
            };
            conf.selector.each(function (index, element) {
                $(this).blur(function () {
                    _this.isOk($(this));
                });
            });
            this.validateAll = function () {
                var ok = true;
                conf.selector.each(function (index, element) {
                    if (!_this.isOk($(element)))
                        ok = false;
                });
                return ok;
            };
            this.setType = function (tp) {
                type = $.extend(type, tp);
            };
            this.setTag = function (tag) {
                conf.tag = tag;
            };
            this.setError = function (fun) {
                conf.error = fun;
            };
            return this;
        }
    });
})(jQuery);