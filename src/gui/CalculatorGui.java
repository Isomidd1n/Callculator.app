package gui;

import constants.CommonConstants;
import service.CalculatorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGui extends JFrame implements ActionListener {
    private final SpringLayout springLayout = new SpringLayout();
    private CalculatorService calculatorService;

    private JTextField addDisplayFild;
    private JButton[] buttons;
    private boolean presedOperator = false;
    private boolean presedEquals = false;

    public CalculatorGui() {
        super(CommonConstants.APP_NAME);
        setSize(CommonConstants.APP_SIZE[0], CommonConstants.APP_SIZE[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(springLayout);

        calculatorService = new CalculatorService();
        addGuiComponents();
    }

    private void addGuiComponents() {
        addDisplayComponents();
        addButtonComponents();
    }

    private void addButtonComponents() {
        GridLayout gridLayout = new GridLayout(CommonConstants.BUTTON_ROWKOUNT, CommonConstants.BUTTON_COLCOUNT);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(gridLayout);
        buttons = new JButton[CommonConstants.BUTTON_COUNT];
        for (int i = 0; i < CommonConstants.BUTTON_COUNT; i++) {
            JButton button = new JButton(getButtonLabel(i));

            button.setFont(new Font("Dialog", Font.PLAIN, CommonConstants.BUTTON_FONTSIZE));
            button.addActionListener(this);

            buttonPanel.add(button);
        }

        gridLayout.setHgap(CommonConstants.BUTTON_HGAP);
        gridLayout.setVgap(CommonConstants.BUTTON_VGAP);

        this.getContentPane().add(buttonPanel);
        springLayout.putConstraint(SpringLayout.NORTH, buttonPanel, CommonConstants.BUTTON_SPRINGLAYOUT_NORTHPAD, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, buttonPanel, CommonConstants.BUTTON_SPRINGLAYOUT_WESTPAD, SpringLayout.WEST, this);
    }

    private void addDisplayComponents() {
        JPanel displayFileComponents = new JPanel();
        addDisplayFild = new JTextField(CommonConstants.TEXT_LENGTH);
        addDisplayFild.setFont(new Font("Dialog", Font.PLAIN, CommonConstants.TEXT_FONTS));
        addDisplayFild.setEditable(false);
        addDisplayFild.setText("0");
        addDisplayFild.setHorizontalAlignment(SwingConstants.RIGHT);

        displayFileComponents.add(addDisplayFild);

        this.getContentPane().add(displayFileComponents);
        springLayout.putConstraint(SpringLayout.NORTH, addDisplayFild, CommonConstants.TEXT_SPRINGLAYOUT_NORTH, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, addDisplayFild, CommonConstants.TEXT_SPRINGLAYOUT_WESTPAD, SpringLayout.WEST, this);

    }

    public String getButtonLabel(int buttonIndex) {
        switch (buttonIndex) {
            case 0:
                return "7";
            case 1:
                return "8";
            case 2:
                return "9";
            case 3:
                return "-";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "+";
            case 8:
                return "1";
            case 9:
                return "2";
            case 10:
                return "3";
            case 11:
                return "*";
            case 12:
                return "0";
            case 13:
                return ".";
            case 14:
                return "=";
            case 15:
                return "/";
        }
        return "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonComment = e.getActionCommand();
        if (buttonComment.matches("[0-9]")) {
            if (presedEquals || presedOperator || addDisplayFild.getText().equals("0")) {
                addDisplayFild.setText(buttonComment);
            } else {
                addDisplayFild.setText(addDisplayFild.getText() + buttonComment);
                presedEquals = false;
                presedOperator = false;
            }
        } else if (buttonComment.equals("=")) {
            calculatorService.setNum2(Double.parseDouble(addDisplayFild.getText()));

            double result = 0;
            switch (calculatorService.getMathSymbol()) {
                case '+':
                    result = calculatorService.add();
                    break;
                case 'x':
                    result = calculatorService.subtract();
                    break;
                case '-':
                    result = calculatorService.multiply();
                    break;
                case '/':
                    result = calculatorService.divide();
                    break;
            }
            addDisplayFild.setText(Double.toString(result));

            presedOperator=true;
            presedEquals=false;


        }else if (buttonComment.equals(".")){
            if (!addDisplayFild.getText().contains(".")){
                addDisplayFild.setText(addDisplayFild.getText() + buttonComment);
            }else {
                if (!presedOperator){
                    calculatorService.setNum1(Double.parseDouble(addDisplayFild.getText()));
                    calculatorService.setMathSymbol(buttonComment.charAt(0));

                    presedOperator=true;
                    presedEquals=false;
                }
            }
        }
    }
}
