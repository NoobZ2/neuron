package neuron
import breeze.stats.distributions._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import neuron.math._
import neuron.autoencoder._
import neuron.core._

@RunWith(classOf[JUnitRunner])
class AutoEncoderTest extends FunSuite with Optimizable with Workspace with EncoderWorkspace {
	test("Test auto-encoder using Array[NeuronVector]") {
	  val inputDimension = 20
	  val hiddenDimension = 10
	  nn = new LinearAutoEncoder(0.1, 0.1)(inputDimension,hiddenDimension)().create()
	  //nn = new SparseLinearAE(0.0,1.0,1.0)(inputDimension,hiddenDimension)().create()
	  //nn = new SparseLinearAE(1.0,1.0,1.0)(inputDimension,hiddenDimension)().create() // Gradient check succeed
	  
	  val numOfSamples = 100
	  xData = new Array(numOfSamples);
	  for (i<- 0 until numOfSamples) {
	    xData(i) = new NeuronVector(nn.inputDimension, new Uniform(-1,1))  
	  }
	  yData = xData
	  
	  val w = gradCheck(1E-6)
	  
	  val time = System.currentTimeMillis();
	  val (obj3, w2) = train(w)
	  println(System.currentTimeMillis() - time, obj3)
	  //println(w.data)
	  //println(w2.data)
	  

	}
	
	test("Test MLP_AE") {
	  nn = new MLP_AE(IndexedSeq(12,5,6,12), IndexedSeq(false, true, true))(
	      (i,j) => (new SingleLayerNeuralNetwork(j) ** new LinearNeuralNetwork(i,j))).extract().create()
	      
	  val numOfSamples = 100
	  xData = new Array(numOfSamples);
	  yData = new Array(numOfSamples);
	  for (i<- 0 until numOfSamples) {
	    xData(i) = new NeuronVector(nn.inputDimension, new Uniform(-1,1)) 
	    yData(i) = new NeuronVector(nn.outputDimension, new Uniform(-1,1)) 
	  }
	  
	  val w = gradCheck(1E-6)
	  
	  val time = System.currentTimeMillis();
	  val (obj3, w2) = train(w)
	  println(System.currentTimeMillis() - time, obj3)	      
	}
}
