define("ace/mode/st_highlight_rules",["require","exports","module","ace/lib/oop","ace/lib/lang","ace/mode/text_highlight_rules"], function(require, exports, module){
"use strict";
var oop = require("../lib/oop");
var lang = require("../lib/lang");
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;
var stHighlightRules = function () {
    this.$rules = {
      "start": [{
        token: "string.regexp",
        regex: /^.*?(?=\=)/
      }, {
        token: "constant.character",
        regex: /\+|\!|\?/ 
      } , {
        token: "string.regexp",
        regex: /\.[^\?].*?(?=\()/
      } , {
        token: "string.regexp",
        regex: /\C\d{3}/
      }, 
      // {
        // token: "variable.parameter",
        // regex: /\(.*?(?=\:)/,
      // } ,
       {
        token: "storage.type",
        regex: /\:.*?(?=\()/,
      }, {
        token: "string.double",
        regex: /(\")(.*?)(\")/,
      }],
      // "constructs": [{
      //   token: "constant.character",
      //   regex: /\+\{|\,/,
      //   next: "internalChoice",
      // },{
      //   token: "constant.character",
      //   regex: /\&/,
      //   next: "externalChoice"
      // }],
      // "internalChoice": [{
      //   token: "string.regexp",
      //   regex: /!.*?(?=\()/,
      //   next: "internalChoiceVariable",
      // }],
      // "internalChoiceVariable": [{
      //   token: "variable.parameter",
      //   regex: /\(.*?(?=\:)/,
      //   next: "type"
      // }],
      // "type": [{
      //   token: "storage.type",
      //   regex: /.*?(?=\()/,
      //   next: "function"
      // }],
      // "function": [{
      //   token: "meta.function",
      //   regex: /.*?(?=\ )/,
      //   next: "variable"
      // }],
      // "variable": [{
      //   token: "string.double",
      //   regex: /(\")(.*?)(\")/,
      // }]
    };
};
oop.inherits(stHighlightRules, TextHighlightRules);
exports.stHighlightRules = stHighlightRules;

});

define("ace/mode/matching_brace_outdent",["require","exports","module","ace/range"], function(require, exports, module){"use strict";
var Range = require("../range").Range;
var MatchingBraceOutdent = function () { };
(function () {
    this.checkOutdent = function (line, input) {
        if (!/^\s+$/.test(line))
            return false;
        return /^\s*\}/.test(input);
    };
    this.autoOutdent = function (doc, row) {
        var line = doc.getLine(row);
        var match = line.match(/^(\s*\})/);
        if (!match)
            return 0;
        var column = match[1].length;
        var openBracePos = doc.findMatchingBracket({ row: row, column: column });
        if (!openBracePos || openBracePos.row == row)
            return 0;
        var indent = this.$getIndent(doc.getLine(openBracePos.row));
        doc.replace(new Range(row, 0, row, column - 1), indent);
    };
    this.$getIndent = function (line) {
        return line.match(/^\s*/)[0];
    };
}).call(MatchingBraceOutdent.prototype);
exports.MatchingBraceOutdent = MatchingBraceOutdent;

});

define("ace/mode/st",["require","exports","module","ace/lib/oop","ace/mode/text","ace/mode/text_highlight_rules","ace/mode/st_highlight_rules","ace/mode/matching_brace_outdent"], function(require, exports, module){
"use strict";
var oop = require("../lib/oop");
var TextMode = require("./text").Mode;
var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;
var stHighlightRules = require("./st_highlight_rules").stHighlightRules;
var MatchingBraceOutdent = require("./matching_brace_outdent").MatchingBraceOutdent;
var Mode = function () {
    this.HighlightRules = stHighlightRules;
    this.$outdent = new MatchingBraceOutdent();
    this.$behaviour = this.$defaultBehaviour;
};
oop.inherits(Mode, TextMode);
(function () {
    this.lineCommentStart = "%";
    this.getNextLineIndent = function (state, line, tab) {
        return this.$getIndent(line);
    };
    this.allowAutoInsert = function () {
        return false;
    };
    this.$id = "ace/mode/st";
    this.snippetFileId = "ace/snippets/tex";
}).call(Mode.prototype);
exports.Mode = Mode;

});                (function() {
                    window.require(["ace/mode/st"], function(m) {
                        if (typeof module == "object" && typeof exports == "object" && module) {
                            module.exports = m;
                        }
                    });
                })();
            