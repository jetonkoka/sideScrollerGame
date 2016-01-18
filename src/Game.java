
//Jeton Koka



import java.applet.*;
public class Game
{
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private int choice;
  private String user;
  private String avoid;
  private String get; 
  private AudioClip bgm;
  private boolean playSound;
  
  public Game()
  {
    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    choice=1;
    playSound=false;
  }
  
  public void playGeneral()
  {
      begChoice();
      if(choice==1)
      {
          user="basedgod.gif";
          avoid="thugs.gif";
          get="soup.gif";
          grid.setImage(new Location(userRow, 0), user);    
          play1();
      }
      else if(choice==2)
      {
          user="doge1.gif";
          avoid="animal control.gif";
          get="bone.gif";
          grid.setImage(new Location(userRow, 0), user);     
          play2();
      }
  }
  
  private int difficultyLevel(String input)
  {
      if(input!=null)
      {
          if(input.equals("1"))
              return 600;
          else if(input.equals("2"))
              return 400;
          else if(input.equals("3"))
              return 200;
      }
      return 700;
  }
  
  //based mode
  public void play1()
  { 
    String input=grid.showInputDialog("Which difficulty would you like? Press 1 for easy. Press 2 for medium. Press 3 for hard\nIf you don't enter a valid option, easy will automatically be chosen for you.");
    int z=difficultyLevel(input);
    
    if(grid.showInputDialog("Do you want music?\nIf so, enter 0. Otherwise, enter anything else").equals("0"))
        music(3);
    
    if(grid.showInputDialog("Do you want sound effects?\n(You may experience lag)\nIf so, enter 0. Otherwise, enter anything else").equals("0"))
        playSound=true;
    
    while (!isGameOver())
    {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % z == 0)
          {
             scrollLeft();
             populateRightEdge();
          }
      updateTitle();
      msElapsed += 100;
    }
  }

  //doge mode
  public void play2()
  {
    int z=300;
    
    if(grid.showInputDialog("Do you want music?\nIf so, enter 0. Otherwise, enter anything else").equals("0"))
        music(4);
    
    if(grid.showInputDialog("Do you want sound effects?\nIf so, enter 0. Otherwise, enter anything else").equals("0"))
        playSound=true;
    
    while (!isGameOver())
    {
      grid.pause(100);
      handleKeyPress();     
      if (msElapsed % z == 0)
      {
          scrollLeft();
          populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
      
      if(msElapsed>=10000&&msElapsed<20000)
          user="doge2.gif";            
      else if(msElapsed>=20000&&msElapsed<30000)
          user="doge3.gif";  
      else if(msElapsed>=30000&&msElapsed<40000)
      {
          user="doge4.gif";
          z=200;
      }
      else if(msElapsed>=40000&&msElapsed<50000)
          user="doge5.gif";
      else if(msElapsed>=50000&&msElapsed<60000)      
          user="doge6.gif"; 
      else if(msElapsed>=60000&&msElapsed<70000)
      {
          user="doge7.gif";
          z=100;
      }
      else if(msElapsed>=70000)
          user="doge8.gif";
         
      grid.setImage(new Location(userRow, 0), user);
    }
  }
  
  public boolean isGameOver()
  {
      boolean over=false;
      
      if(timesGet-timesAvoid<0)
      {
          grid.showMessageDialog("Game over! You suck!");
          over=true;
          if(grid.showInputDialog("Do you want to play again?\nIf so, enter 0").equals("0"))
          {
              timesGet++;
              new Game().playGeneral();
              over=true;
          }
          else
          {
              grid.showMessageDialog("Ok I see how it is..."); 
              System.exit(0);               
          }
      }
               
      return over;
  }
  
  public void begChoice()
  {
      while(choice>=1&&choice<=2)
      {         
        try
        {
            choice=Integer.parseInt(grid.showInputDialog("Pick your mode\nbased mode is 1 , doge mode is 2"));
        }
        catch(Exception e)
        {
            grid.showMessageDialog("Y U DO DIS, enter one of the choices!");
            choice=Integer.parseInt(grid.showInputDialog("Pick your mode noob"));
        }
        if(!(choice>=1&&choice<=2))
        {
            grid.showMessageDialog("Y U DO DIS, enter one of the choices!");
            choice=1;
        }
        else
            break;
      }
  }
    
  public void handleKeyPress()
  {
      int key=grid.checkLastKeyPressed();
      
      if(key==38&&userRow>0)
      {
          if(grid.getImage(new Location(userRow-1,0))!=null)
              handleCollision(new Location(userRow-1,0));
                               
          userRow--; 
          grid.setImage(new Location(userRow,0),user);
          grid.setImage(new Location(userRow+1,0),null);
      }           
      else if(key==40&&userRow<grid.getNumRows()-1)
      {
          if(grid.getImage(new Location(userRow+1,0))!=null)
              handleCollision(new Location(userRow+1,0));         
          
          userRow++;
          grid.setImage(new Location(userRow,0),user); 
          grid.setImage(new Location(userRow-1,0),null);
      }    
  }

  public void populateRightEdge()
  {
      int av=(int)(Math.random()*grid.getNumRows());
      int g=(int)(Math.random()*grid.getNumRows());
          
      grid.setImage(new Location(av,grid.getNumCols()-1),avoid); 
      grid.setImage(new Location(g,grid.getNumCols()-1),get);     
  }
  
  private void scrollRowLeft(int r)
  {
      for(int i=0;i<grid.getNumCols();i++)
      {
          boolean derp=false;
          
          if(i==0&&(grid.getImage(new Location(r,0))==null||!grid.getImage(new Location(r,0)).equals(user)))
              grid.setImage(new Location(r,0),null);               
                        
          if(grid.getImage(new Location(r,i))==(null))
              derp=true;
          
          if(derp==false&&i!=0&&(grid.getImage(new Location(r,i)).equals(avoid)||grid.getImage(new Location(r,i)).equals(get)))
          {
              grid.setImage(new Location(r,i-1),grid.getImage(new Location(r,i)));
              grid.setImage(new Location(r,i),null);
          }         
      }
  }
  
  public void scrollLeft()
  {
      handleCollision(new Location(userRow,1));
      
      for(int i=0;i<grid.getNumRows();i++)
          scrollRowLeft(i);
  }
  
  public void handleCollision(Location loc)
  {
      if(grid.getImage(loc)!=null)
      {
          if(grid.getImage(loc).equals(avoid)&&playSound)
          {
              timesAvoid++;
              music(1);
          }
          else if(grid.getImage(loc).equals(avoid))
              timesAvoid++;
              
          
          if(grid.getImage(loc).equals(get)&&playSound)
          {
              timesGet++;
              music(2);
          }
          else if(grid.getImage(loc).equals(get))
              timesGet++;
      }
      
      grid.setImage(loc,null);
  }
  
    public void music(int a)
    {
      if(a==1)
      {
          try
          {
             AudioClip se = Applet.newAudioClip(Game.class.getResource("oops.wav"));
             se.play();
          }
          catch(Exception e)
          {
            System.out.println(e);
          }
      }
      else if(a==2)
      {
            try
            {
                AudioClip re = Applet.newAudioClip(Game.class.getResource("yes.wav"));
                re.play();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
      }
      else if(a==3)
      {
        try
        {
            bgm = Applet.newAudioClip(Game.class.getResource("WontonSoup.wav"));
            bgm.loop();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
      }     
      else if(a==4)
      {
          try
          {
              bgm=Applet.newAudioClip(Game.class.getResource("Focus.wav"));
              bgm.loop();
          }
          catch(Exception e)
          {
              System.out.println(e);
          }
      }
  }
  
  public int getScore()
  {
      return timesGet-timesAvoid;
  }
  
  public void updateTitle()
  {
      grid.setTitle("Game:  "+ getScore()+ "      "+ "Time:  "+ msElapsed/1000);
  }
    
  public static void test()
  {
      Game game = new Game();
      game.playGeneral();
  }
  
  public static void main(String[] args)
  {
      test();
  }
}