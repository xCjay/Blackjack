import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import svu.csc213.Dialog;


public class Blackjack extends GraphicsProgram{


    private JButton playButton, hitButton, stayButton, quitButton;

    private GLabel bankLabel, wagerLabel, balanceLabel;


    private int wager = 0;
    private int balance = 10000;
    private int bank = 10000;



    private Deck deck;
    private GHand player;
    private GHand dealer;




    @Override
    public void init() {
        this.setBackground(Color.pink);

        deck = new Deck();
        dealer = new GHand(new Hand(deck, true));
        player = new GHand(new Hand(deck, false));


        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");



        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);

        addActionListeners();

        bankLabel = new GLabel("Bank: " + bank);
        wagerLabel = new GLabel("Wager: " + wager);
        balanceLabel = new GLabel("Balance: " + balance);

        add(bankLabel, 5, 10);
        add(wagerLabel, 5, 30);
        add(balanceLabel, 5, 50);
        }

    @Override
    public void run() {
        this.getMenuBar().setVisible(false);

        hitButton.setVisible(false);
        stayButton.setVisible(false);



        System.out.println(player.getTotal());
        System.out.println(dealer.getTotal());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()){

            case "Play":
                play();
                break;

            case "Hit":
                hit();
                break;

            case "Stay":
                stay();
                break;

            case "Quit":
                System.exit(0);
                break;

        }
    }



    private void wager() {
        wager = Dialog.getInteger("How much would you like to Wager?");
        while (wager > balance){
            Dialog.showMessage("Invalid Amount, Enter another amount");
            wager = Dialog.getInteger("How much would you like to Wager?");
        }
        wagerLabel.setLabel("Wager: " + wager);

    }

    private void play() {
        wager();

        add(player, 50, getHeight()-player.getHeight());
        add(dealer, 50, dealer.getHeight());
        checkDealerWin();
        checkWin(player.getTotal());
        hitButton.setVisible(true);
        stayButton.setVisible(true);
    }

    private void hit() {
        player.hit();

        checkWin(player.getTotal());

        System.out.println(player.getTotal());
        System.out.println(dealer.getTotal());
    }

    private void stay() {
        dealer.flipCard(0);
        while(dealer.getTotal() <= 17){
            dealer.hit();
            if (dealer.getTotal() > player.getTotal()){
                dealerWin();
            }
            pause(50);
        }
        checkDealerWin();
        checkWin(player.getTotal());

        System.out.println(dealer.getTotal());
        System.out.println(player.getTotal());

    }

    private void checkWin(int a) {
        if (a > 21){
            dealerWin();
        } else if (a == 21) {
            playerWin();
        }
    }

    private boolean checkDealerWin(){
        if (dealer.getTotal() > 21){
            Dialog.showMessage("Win");
            dealerWin();
            return false;
        } else if (player.getTotal() == 21){
            dealerWin();
            return true;
        } else {
            return false;
        }
    }

    private void playerWin(){
        balance += wager;
        bank -= wager;
        wager = 0;
        refreshLabels();
        Dialog.showMessage("Win");
        restart();
    }

    private void dealerWin(){
        balance -= wager;
        bank += wager;
        wager = 0;
        refreshLabels();
        Dialog.showMessage("Bust");
        restart();
    }

    private void refreshLabels() {
        bankLabel.setLabel("Bank: " + bank);
        wagerLabel.setLabel("Wager: " + wager);
        balanceLabel.setLabel("Balance: " + balance);
    }

    private void restart(){
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        deck.shuffle();

    }

    public static void main(String[] args) {
        new Blackjack().start();
    }
}
