S_restcountries=!RestApi.getAll().?C200(countries: "Seq[GetAll]")<util.setCountries(countries)>.
                rec X.(+{!RestApi.getByAlphacode(alphacode: String(getAlphaCode)).
                        &{?C200(countries: GetAll).X,
                        ?C404()<util.assertRandomAlphaCode()>.X},
                !RestApi.getByRegion(region: String(getRegion)).
                        &{?C200(countries: "Seq[GetAll]").X,
                        ?C404()<util.assertEmptyRegion()>.X},
                !RestApi.getByCapital(capital: String(getCapital)).
                        &{?C200(countries: "Seq[GetAll]").X,
                        ?C404()<util.assertEmptyCapital()>.X},
                !RestApi.getByCountryName(name: String(getCountryName)).
                        &{?C200(countries: "Seq[GetAll]").X,
                        ?C404()<util.assertEmptyCountryName()>.X},
                !RestApi.getByCurrency(currency: String(getCurrency)).
                        &{?C200(countries: "Seq[GetAll]").X,
                        ?C404()<util.assertEmptyCurrency()>.X},
                !RestApi.getByLang(lang: String(getLang)).
                        &{?C200(countries: "Seq[GetAll]").X,
                        ?C404()<util.assertEmptyLang()>.X},
                !RestApi.getByAlphacode(alphacode: String(getAlphaCode)).
                        &{?C200(countries: GetAll),
                        ?C404()<util.assertRandomAlphaCode()>}})