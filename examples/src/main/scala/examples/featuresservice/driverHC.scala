package examples.featuresservice

import examples.featuresservice.api._
import examples.featuresservice.model._
import sttp.client3._

import scala.util.control.TailCalls.{TailRec, done, tailcall}

class driverHC(setup: => Unit, teardown: => Unit, max: Int, var seed: Int, repetitions: Int, report: String => Unit) extends Runnable {
	object info {
		object POST_product_50 {
			var productName: String = _
			var prob = 1.0
		}
		object C201_1_49 {
			var freq = 0
		}
		object POST_configuration_42 {
			var configName: String = _
			var prob = 0.3
		}
		object C201_2_41 {
			var freq = 0
		}
		object GET_config_2_38 {
			var prob = 0.8
		}
		object C200_6_37 {
			var retrievedConfigName: String = _
			var valid: Boolean = _
			var freq = 0
		}
		object POST_feature_1_36 {
			var featureName1: String = _
			var prob = 1.0
		}
		object C201_3_35 {
			var freq = 0
		}
		object POST_feature_2_26 {
			var featureName2: String = _
			var prob = 0.5
		}
		object C201_4_25 {
			var freq = 0
		}
		object POST_feature_constraint_12 {
			var prob = 0.4
		}
		object C201_5_11 {
			var constraintID: Int = _
			var freq = 0
		}
		object POST_config_feature_1_10 {
			var prob = 1.0
		}
		object C201_6_9 {
			var freq = 0
		}
		object GET_config_features_1_8 {
			var prob = 1.0
		}
		object C200_1_7 {
			var features: String = _
			var freq = 0
		}
		object DELETE_config_feature_1_6 {
			var prob = 1.0
		}
		object C204_1_5 {
			var freq = 0
		}
		object GET_config_features_2_4 {
			var prob = 1.0
		}
		object C200_3_3 {
			var features: String = _
			var freq = 0
		}
		object DELETE_constraint_1_2 {
			var prob = 1.0
		}
		object C204_4_1 {
			var freq = 0
		}
		object POST_feature_exclude_20 {
			var prob = 0.4
		}
		object C201_7_19 {
			var freq = 0
		}
		object POST_config_feature_2_18 {
			var prob = 1.0
		}
		object C201_8_17 {
			var freq = 0
		}
		object POST_config_feature_3_16 {
			var prob = 1.0
		}
		object C403_1_15 {
			var freq = 0
		}
		object GET_config_1_14 {
			var prob = 1.0
		}
		object C200_4_13 {
			var valid: Boolean = _
			var freq = 0
		}
		object DELETE_configuration_24 {
			var prob = 0.2
		}
		object C204_3_23 {
			var freq = 0
		}
		object GET_configurations_22 {
			var prob = 1.0
		}
		object C200_5_21 {
			var configs: String = _
			var freq = 0
		}
		object PUT_feature_1_28 {
			var description: String = _
			var prob = 0.15
		}
		object C200_7_27 {
			var updatedFeatureName: String = _
			var updatedDescription: String = _
			var freq = 0
		}
		object PUT_non_exist_feature_1_30 {
			var featureName3: String = _
			var description: String = _
			var prob = 0.15
		}
		object C404_1_29 {
			var freq = 0
		}
		object DELETE_feature_1_32 {
			var prob = 0.1
		}
		object C204_5_31 {
			var freq = 0
		}
		object POST_duplicated_feature_34 {
			var prob = 0.1
		}
		object C400_1_33 {
			var freq = 0
		}
		object POST_duplicated_configuration_40 {
			var prob = 0.2
		}
		object C400_2_39 {
			var freq = 0
		}
		object GET_products_44 {
			var prob = 0.4
		}
		object C200_8_43 {
			var products: String = _
			var freq = 0
		}
		object GET_product_48 {
			var prob = 0.3
		}
		object C200_2_47 {
			var retrievedProductName: String = _
			var freq = 0
		}
		object DELETE_product_46 {
			var prob = 1.0
		}
		object C204_2_45 {
			var freq = 0
		}
	}
	abstract class DriverException(choice: Any, message: String) extends Exception {
		def getInfo: (Any, String) = {
			(choice, message)
		}
	}
	class InvalidMessageException(choice: Any, message: String) extends DriverException(choice, message)
	class AssertionViolationException(choice: Any, message: String) extends DriverException(choice, message)
	var InvalidMessageExceptions:collection.mutable.Map[Any, List[InvalidMessageException]] = collection.mutable.Map()
	var AssertionViolationExceptions:collection.mutable.Map[Any, List[AssertionViolationException]] = collection.mutable.Map()
	var r = new scala.util.Random(seed)
	var passed = true
	var pass = 0.0
	var fail = 0.0
	val animationChars = List[Char]('|', '/', '-', '\\')
	val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
	val defaultApi = DefaultApi.apply()
	override def run(): Unit = {
		report("[DRIVER] Starting tests...\n")
		report("\n")
		for(rep <- 1 to repetitions){
			report("\u001b[1A\u001b[2K")
			report(f"[DRIVER] Running test: $rep ${animationChars(rep % 4)}\n")
			val sequence = new StringBuilder()
			try {
				sendPOST_product_50(0, sequence).result
				pass+=1
			} catch {
				case e: InvalidMessageException =>
					InvalidMessageExceptions.update(e.getInfo._1, InvalidMessageExceptions.getOrElse(e.getInfo._1, List()):+e)
					sequence.append(e.getInfo._2)
					passed=false; fail+=1
					teardown
				case e: AssertionViolationException =>
					AssertionViolationExceptions.update(e.getInfo._1, AssertionViolationExceptions.getOrElse(e.getInfo._1, List()):+e)
					sequence.append(e.getInfo._2)
					passed=false; fail+=1
					teardown
				case e: Throwable =>
					sequence.append(e.getMessage)
					println(sequence)
					teardown
					throw new Exception("Error in test")
			}
			println(sequence)
			passed=true; seed=r.nextInt(); r=new scala.util.Random(seed)
		}
		reportSummary()
	}
	def reportSummary(): Unit = {
		report("[DRIVER] TESTS SUMMARY\n")
		report("Number of tests: "+repetitions+"\n")
		report("Passed (%): "+(pass/repetitions.toDouble)*100+"\n")
		report("Failed (%): "+(fail/repetitions.toDouble)*100+"\n")
		report("Invalid Message violations: "+InvalidMessageExceptions.size+"\n")
		report("Assertion violations: "+AssertionViolationExceptions.size+"\n")
	}
	def sendPOST_product_50(count: Int, sequence: StringBuilder): TailRec[Unit] = {
		var productName = util.const("Product_1",r)
		val response = defaultApi.addProduct(productName).send(backend); sequence.append(f"!POST_product($productName).")
		info.POST_product_50.productName = productName
		if (count < max) {
			receiveC201_1_49(response.code.toString.toInt, null, count+1, sequence)
		} else { tailcall(receiveC201_1_49(response.code.toString.toInt, asInstanceOf[Right[Any, Any]].value.asInstanceOf[Null], 0, sequence)) }
	}
	def receiveC201_1_49(responseCode: Int, body: Null, count: Int, sequence: StringBuilder): TailRec[Unit] = {
		responseCode match {
			case msg @ 201 =>
				info.C201_1_49.freq+=1
				sequence.append(f"?$msg(${body.toString}).")
				if (count < max) {
					sendInternalChoice4(count+1, sequence)
				} else { tailcall(sendInternalChoice4(0, sequence)) }
			case msg @ _ => sequence.append(f"?$msg."); passed=false; throw new InvalidMessageException(201, f"Unknown message: $msg");
		}
	}
	def sendInternalChoice4(count: Int, sequence: StringBuilder): TailRec[Unit] = {
		val rand = r.nextDouble()
		println(rand);
		if (rand <= info.POST_configuration_42.prob){
			var configName = util.configName(r)
			val response = defaultApi.addConfiguration(info.POST_product_50.productName, configName).send(backend); sequence.append(f"!POST_configuration(${info.POST_product_50.productName}, $configName).")
			info.POST_configuration_42.configName = configName
			if (count < max) {
				receiveC201_2_41(response.code.toString.toInt, count+1, sequence)
			} else { tailcall(receiveC201_2_41(response.code.toString.toInt, 0, sequence)) }
		} else if (rand <= info.GET_products_44.prob+0.3){
			val response = defaultApi.getAllProducts().send(backend); sequence.append(f"!GET_products().")
			if (count < max) {
				receiveC200_8_43(response.code.toString.toInt, response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[Seq[String]], count+1, sequence)
			} else { tailcall(receiveC200_8_43(response.code.toString.toInt, response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[Seq[String]], 0, sequence)) }
		} else if (rand <= info.GET_product_48.prob+0.7){
			sequence.append(f"!GET_product(${info.POST_product_50.productName}).")
			val response = defaultApi.getProductByName(info.POST_product_50.productName).send(backend)
			if (count < max) {
				receiveC200_2_47(response.code.toString.toInt, response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[Product], count+1, sequence)
			} else { tailcall(receiveC200_2_47(response.code.toString.toInt, response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[Product], 0, sequence)) }
		} else { throw new Exception("[DRIVER] Error in test") }
	}
	def receiveC201_2_41(responseCode: Int, count: Int, sequence: StringBuilder): TailRec[Unit] = {
		responseCode match {
			case msg @ 201 =>
				info.C201_2_41.freq+=1
				sequence.append(f"?$msg.")
				done()
			case msg @ _ => sequence.append(f"?$msg."); passed=false; throw new InvalidMessageException(201, f"Unknown message: $msg");
		}
	}
	def receiveC200_8_43(responseCode: Int, responseBody: Seq[String], count: Int, sequence: StringBuilder): TailRec[Unit] = {
		responseCode match {
			case msg @ 200 =>
				if(responseBody.contains(info.POST_product_50.productName)){
					info.C200_8_43.freq+=1
					sequence.append(f"?$msg($responseBody).")
					done()
				} else {
					passed=false; throw new AssertionViolationException(200, "Violation in Assertion: products.contains(productName)");  }
			case msg @ _ => sequence.append(f"?$msg."); passed=false; throw new InvalidMessageException(200, f"Unknown message: $msg");
		}
	}
	def receiveC200_2_47(responseCode: Int, responseBody: Product, count: Int, sequence: StringBuilder): TailRec[Unit] = {
		responseCode match {
			case msg @ 200 =>
				if(info.POST_product_50.productName==responseBody.name.get){
					info.C200_2_47.freq+=1
					sequence.append(f"?$msg.")
					done()
				} else {
					passed=false; throw new AssertionViolationException(200, "Violation in Assertion: productName==retrievedProductName");  }
			case msg @ _ => sequence.append(f"?$msg."); passed=false; throw new InvalidMessageException(200, f"Unknown message: $msg");
		}
	}
}
