define(function(require, exports, module) {

  "use strict";
  
  var oop = require("../lib/oop");
  var TextHighlightRules = require("./text_highlight_rules").TextHighlightRules;
  
  var STHighlightRules = function () {
      var keywordMapper = this.createKeywordMapper({
          "keyword": "rec | String",
          "constant.language": "stLanguage",
          "keyword.operator": "+"
      }, "text", true, " ");;
      this.$rules = {
          "start": [{
            token: "name",
            regex: "^.*?(?=\=)",
            next: "constructs"
        }, ],
        "constructs": [{
          token: "keyword.operator",
          regex: "+|!",
          next: "internalChoice"
        },{
          token: "externalChoiceConstruct",
          regex: "&|?",
          next: "externalChoice"
        }],
        "internalChoice": [{
  
        }]
      };
  };
  oop.inherits(STHighlightRules, TextHighlightRules);
  
  exports.STHighlightRules = STHighlightRules;
  });