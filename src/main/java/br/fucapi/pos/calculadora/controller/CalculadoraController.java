package br.fucapi.pos.calculadora.controller;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CalculadoraController {
	private Op operacao = Op.NONE;
	private BigDecimal anterior = BigDecimal.ZERO;
	private String atual = BigDecimal.ZERO.toString();
	@FXML private Text saida;
	
	public CalculadoraController() {
	}
	
	@FXML
	public void initialize() {
		saida.setText(atual);
	}
	
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {
		Button btn = (Button)event.getSource();
		String comando = btn.getText();
		
		switch (comando) {
		case "/":
		case "*":
		case "-":
		case "+":
			if (atual.length() > 0) {
				anterior = new BigDecimal(atual);
				atual = BigDecimal.ZERO.toString();
			}
			operacao = Op.getOp(comando);
			break;
		case ".":
			if (!atual.contains(".")) {
				atual = atual + comando;
			}
			break;
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			if (atual.equals("0")) {
				atual = comando;
			} else {
				atual = atual + comando;
			}
			break;
		case "C":
			anterior = BigDecimal.ZERO;
			operacao = Op.NONE;
		case "CE":
			atual = BigDecimal.ZERO.toString();
			break;
		case "=":
			atual = operacao.apply(anterior, new BigDecimal(atual)).toString();
			operacao = Op.NONE;
		default:
			break;
		}
		
		saida.setText(atual);
	}
	
	private enum Op {
		NONE,
		DIVIDE,
		MULTIPLY,
		SUBTRACT,
		SUM;
		
		public static Op getOp(String operacao) {
			switch (operacao) {
			case "/":
				return DIVIDE;
			case "*":
				return MULTIPLY;
			case "-":
				return SUBTRACT;
			case "+":
				return SUM;
			default:
				return NONE;
			}
		}
		
		public BigDecimal apply(BigDecimal first, BigDecimal second) {
			switch (this) {
			case DIVIDE:
				return first.divide(second);
			case MULTIPLY:
				return first.multiply(second);
			case SUBTRACT:
				return first.subtract(second);
			case SUM:
				return first.add(second);
			default:
				return second;
			}
		}

	}
}
