S_restcountries=+{!DefaultApi.v1All().?C200(countries: "Seq[CountryV1]")<util.setCountriesV1(countries)>.
                    rec X.(+{!DefaultApi.v1Alphacode(alphaCode: String(getAlpha2code)).?C200().
                        !DefaultApi.v1Alphacode(alphaCode: String(getWrongAlpha2Code)).?C404().
                        !DefaultApi.v1Alphacode(alphaCode: String(getBadAlpha2Code)).?C400().X,
                    !DefaultApi.v1Alphacodes(alphaCodes: String(getAlpha2codes)).?C200().
                        +{!DefaultApi.v1Alphacodes(alphaCode: String(getWrongAlpha2Code)).?C404().X,
                        !DefaultApi.v1Alphacodes(alphaCode: String(getBadAlpha2Code)).?C400().X},
                    !DefaultApi.v1Callingcode(callingCode: String(getCallingCode)).?C200().
                        !DefaultApi.v1Callingcode(callingCode: String(getWrongCallingCode)).?C404().X,
                    !DefaultApi.v1Capital(capital: String(getCapital)).
                        &{?C200().!DefaultApi.v1Capital(capital: String(getWrongCapital)).?C404().X,
                        ?C404()<util.assertEmpty(capital)>.X},
                    !DefaultApi.v1Currency(currency: String(getCurrency)).?C200().
                        !DefaultApi.v1Currency(currency: String(getBadCurrency)).?C400().
                        !DefaultApi.v1Currency(currency: String(getWrongCurrency)).?C404().X,
                    !DefaultApi.v1Lang(lang: String(getLang)).
                        &{?C200().X,
                        ?C404()<util.assertEmpty(lang)>.X},
                    !DefaultApi.v1Region(region: String(getRegion)).
                        &{?C200().X,
                        ?C404()<util.assertEmpty(region)>.X},
                    !DefaultApi.v1Subregion(subregion: String(getSubregion)).
                        &{?C200().X,
                        ?C404()<util.assertEmpty(subregion)>.X},
                    !DefaultApi.v1Name(name: String(getName)).?C200()}),
                !DefaultApi.v2All().?C200(countries: "Seq[CountryV2]")<util.setCountriesV2(countries)>.
                    rec X.(+{!DefaultApi.v2Alphacode(alphaCode: String(getAlpha2codeV2)).?C200().
                        !DefaultApi.v2Alphacode(alphaCode: String(getWrongAlpha2CodeV2)).?C404().
                        !DefaultApi.v2Alphacode(alphaCode: String(getBadAlpha2CodeV2)).?C400(),
                    !DefaultApi.v2Alphacodes(alphaCodes: String(getAlpha2codesV2)).?C200().
                        +{!DefaultApi.v2Alphacodes(alphaCode: String(getWrongAlpha2CodeV2)).?C404().X,
                        !DefaultApi.v2Alphacodes(alphaCode: String(getBadAlpha2CodeV2)).?C400().X},
                    !DefaultApi.v2Callingcode(callingCode: String(getCallingCodeV2)).?C200().
                        !DefaultApi.v2Callingcode(callingCode: String(getWrongCallingCodeV2)).?C404().X,
                    !DefaultApi.v2Capital(capital: String(getCapitalV2)).
                        &{?C200().!DefaultApi.v2Capital(capital: String(getWrongCapitalV2)).?C404().X,
                        ?C404()<util.assertEmptyV2(capital)>.X},
                    !DefaultApi.v2Currency(currency: String(getCurrencyV2)).?C200().
                        !DefaultApi.v2Currency(currency: String(getBadCurrencyV2)).?C400().
                        !DefaultApi.v2Currency(currency: String(getWrongCurrencyV2)).?C404().X,
                    !DefaultApi.v2Lang(lang: String(getLangV2)).
                        &{?C200().!DefaultApi.v2Lang(lang: String(getWrongLangV2)).?C404().X,
                        ?C404()<util.assertEmptyV2(lang)>.X},
                    !DefaultApi.v2Region(region: String(getRegionV2)).
                        &{?C200().!DefaultApi.v2Region(region: String(getWrongRegionV2)).?C404().X,
                        ?C404()<util.assertEmptyV2(region)>.X},
                    !DefaultApi.v2Subregion(subregion: String(getSubregionV2)).
                        &{?C200().!DefaultApi.v2Subregion(subregion: String(getWrongSubregionV2)).?C404().X,
                        ?C404()<util.assertEmptyV2(subregion)>.X},
                    !DefaultApi.v2Demonym(demonym: String(getDemonym)).
                        &{?C200().!DefaultApi.v2Demonym(demonym: String(getWrongDemonym)).?C404().X,
                        ?C404()<util.assertEmptyV2(demonym)>.X},
                    !DefaultApi.v2Regionalbloc(regionalBloc: String(getRegionalBloc)).
                        &{?C200().!DefaultApi.v2Regionalbloc(regionalBloc: String(getWrongRegionalBloc)).?C404().X,
                        ?C404()<util.assertEmptyV2(regionalBloc)>.X},
                    !DefaultApi.v2Name(name: String(getNameV2)).?C200().
                       !DefaultApi.v2Name(name: String(getWrongNameV2)).?C404()})}