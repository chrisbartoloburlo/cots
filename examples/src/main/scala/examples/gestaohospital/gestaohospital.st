S_gestaohospital=!HospitalResourceApi.findAllUsingGET().?C200(hospitals: "Seq[HospitalDTO]")<util.setHospitals(hospitals)>.
                    !HospitalResourceApi.findByIdUsingGET(id: String(getRandomHospitalId)).?C200(hospital: HospitalDTO).
                    !HospitalResourceApi.findByIdUsingGET(wrongHospitalId: String(getWrongHospitalId)).?C404().
                    !HospitalResourceApi.insertUsingPOST(objDTO: HospitalDTO(getHospitalDTO)).?C200(hospital: HospitalDTO)<util.setHospital(hospital)>.
                    !HospitalResourceApi.verificaLeitosDisponiveisUsingGET(id: String(getHospitalId)).?C200(beds: "Map[String, Int]").
                    !HospitalResourceApi.verificaLeitosDisponiveisUsingGET(wrongHospitalId: String(getWrongHospitalId)).?C400().
                    !HospitalResourceApi.updateUsingPUT(id, objDTO: HospitalDTO(getHospitalDTO)).?C200(hospital: HospitalDTO)<util.setHospital(hospital)>.
                    !PatientResourceApi.checkinPacientUsingPOST(hospitalId: String(getHospitalId), patient: Patient(getPatient)).?C200(patient: Patient)<util.setPatient(patient)>.
                    !PatientResourceApi.checkinPacientUsingPOST(wrongHospitalId, patient: Patient(getPatient)).?C404().
                    !PatientResourceApi.findPatientsUsingGET(hospitalId).?C200(patients: "Seq[Patient]").
                    !PatientResourceApi.findPatientsUsingGET(wrongHospitalId).?C404().
                    !PatientResourceApi.findPatientByIdUsingGET(hospitalId, patientId: String(getPatientId)).?C200(patient: Patient)<util.assertCorrectPatient(patient)>.
                    !PatientResourceApi.updatePatientUsingPUT(hospitalId, patientId, patient: Patient(getPatient)).?C200(patient: Patient).
                    !PatientResourceApi.updatePatientUsingPUT(hospitalId, wrongPatientId: String(getWrongPatientId), patient: Patient(getPatient)).?C404().
                    !PatientResourceApi.checkoutPacientUsingPOST(hospitalId, patientId).?C200(patient: Patient).
                    !PatientResourceApi.checkoutPacientUsingPOST(hospitalId, wrongPatientId).?C404().
                    !ProductResourceApi.findAllProductByUsingGET(hospitalId).?C200(products: "Seq[ProductDTO]").
                    !ProductResourceApi.findAllProductByUsingGET(wrongHospitalId).?C400().
                    !ProductResourceApi.insertUsingPOST1(hospitalId, product: ProductDTO(getProduct)).?C200(product: ProductDTO)<util.setProduct(product)>.
                    !ProductResourceApi.insertUsingPOST1(wrongHospitalId, product: ProductDTO(getProduct)).?C400().
                    !ProductResourceApi.findProductByUsingGET(hospitalId, productId: String(getProductId)).?C200(product: ProductDTO).
                    !ProductResourceApi.findProductByUsingGET(hospitalId, wrongProductId: String(getWrongProductId)).?C404().
                    !ProductResourceApi.updateUsingPUT1(hospitalId, productId, product: ProductDTO(getProduct)).?C200(product: ProductDTO)<util.setProduct(product)>.
                    !ProductResourceApi.updateUsingPUT1(hospitalId, wrongProductId, product: ProductDTO(getProduct)).?C400().
                    !HospitalResourceApi.transferenciaProdutoUsingPOST(hospitalId, productId, amount: String(getAmount)).?C404().
                    !HospitalResourceApi.transferenciaProdutoUsingPOST(hospitalId, productId, amount: String(getAmount)).?C404().
                    !ProductResourceApi.insertUsingPOST1(tmpHospitalId: String(constant "1"), product: ProductDTO(getProduct)).?C200(product: ProductDTO)<util.setProductTransfer(product)>.
                    !ProductResourceApi.insertUsingPOST1(tmpHospitalId: String(constant "2"), product).?C200(product: ProductDTO).
                    !ProductResourceApi.deleteUsingDELETE(hospitalId, productId).?C200().
                    !ProductResourceApi.deleteUsingDELETE(hospitalId, wrongProductId).?C400().
                    !LocationResourceApi.findHospitalNearHospitalByUsingGET(hospitalId, ratio: Double(getRadiusDouble)).?C200(hospitals: "Seq[HospitalDTO]").
                    !LocationResourceApi.findHospitalNearHospitalByUsingGET(wrongHospitalId, ratio: Double(getRadiusDouble)).?C400().
                    !LocationResourceApi.findLocationNearHospitalByUsingGET(hospitalId).?C200(locations: "Seq[LocationDTO]").
                    !LocationResourceApi.findLocationNearHospitalByUsingGET(wrongHospitalId).?C400().
                    !LocationResourceApi.findLocationNearHospitalByUsingGET(tmpHospitalId: String(constant "1")).?C200(locations: "Seq[LocationDTO]").
                    !HospitalResourceApi.deleteByIdUsingDELETE(hospitalId).?C200().
                    !HospitalResourceApi.deleteByIdUsingDELETE(wrongHospitalId).?C400().
                    !HospitalResourceApi.hospitalMaisProximoUsingGET(lat: String(getLat), lon: String(getLon), raioMaximo: String(getRadius)).?C200(hospital: HospitalDTO)<util.assertCorrect(hospital)>
