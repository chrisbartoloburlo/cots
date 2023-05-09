S_languagetool=rec X.(+{!DefaultApi.languagesGet()<0.9>.?C200(languages: "Seq[LanguagesGet200ResponseInner]")<util.setLanguages(languages)>.
                    !DefaultApi.checkPost(language: String(getLanguage), text: String(genRandomText)).?C200(response: CheckResponse).
                    !DefaultApi.checkPost(langauge: String(getLanguage), text: String(genRandomText), data: String(getNone), u: String(getNone), a: String(getNone), d: String(getDicts)).?C200(response: CheckResponse).
                    !DefaultApi.checkPost(langauge: String(getLanguage), text: String(genRandomText), data: String(getNone), u: String(getNone), a: String(getNone), d: String(getNone), motherTongue: String(getMotherTongue)).?C200(response: CheckResponse).
                    !DefaultApi.checkPost(language: String(getAuto), text: String(genRandomText), data: String(getNone),  u: String(getNone), a: String(getNone), d: String(getNone), motherTongue: String(getNone), preferredVariants: String(getVariants)).?C200(response: CheckResponse).
                    !DefaultApi.checkPost(language: String(getAuto), text: String(genRandomText), data: String(getNone),  u: String(getNone), a: String(getNone), d: String(getNone), motherTongue: String(getNone), preferredVariants: String(getNone), enabledRules: String(getNone), disabledRules: String(getDisabled), enabledCategories: String(getNone), disabledCategories: String(getNone), enabledOnly: Boolean(getEnabled)).?C400().
                    !DefaultApi.checkPost(language: String(getAuto), text: String(genRandomText), data: String(getNone),  u: String(getNone), a: String(getNone), d: String(getNone), motherTongue: String(getNone), preferredVariants: String(getNone), enabledRules: String(getNone), disabledRules: String(getDisabled), enabledCategories: String(getNone), disabledCategories: String(getNone), enabledOnly: Boolean(getBoooleanNone), level: String(getLevel)).?C200(response: CheckResponse).
                    !DefaultApi.checkPost(language, text: String(genRandomText)).?C200(response: CheckResponse).
                    !DefaultApi.checkPost(language, text: String(getNone), data: String(genRandomData)).?C200(response: CheckResponse).
                    !DefaultApi.checkPost(language, text: String(getNone), data: String(genRandomText)).?C400().
                    !DefaultApi.checkPost(language).?C400().
                    !DefaultApi.checkPost(language, text: String(getNone), data: String(getNone)).?C400().
                    !DefaultApi.wordsGet(uname: String(constant "uname"), apiKey: String(constant "apikey")).?C500().
                    !DefaultApi.wordsDeletePost(word: String(constant "word"), uname: String(constant "uname"), apiKey: String(constant "apikey")).?C500().
                    !DefaultApi.wordsAddPost(word: String(constant "word"), uname: String(constant "uname"), apiKey: String(constant "apikey")).?C500().X,
                !DefaultApi.languagesGet()<0.1>.?C200(languages: "Seq[LanguagesGet200ResponseInner]")})

