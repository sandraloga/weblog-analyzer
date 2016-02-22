/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * nuevo constructor tendrá un parámetro consistente en el nombre del archivo de log a analiza
     */
    public LogAnalyzer(String archivoLog)
    {
        hourCounts = new int[24];
        reader = new LogfileReader(archivoLog);
    }

    /**
     * Analyze the hourly access data from the log file.
     */

    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Crea en la clase LogAnalyzer un método llamado numberOfAccesses
     * que se pueda ejecutar después del método analyzeHourlyData y 
     * que devuelva el número total de accesos al servidor web registrados en el archivo de log.
     */
    public int numberOffAccesses()
    {
        int contadorAccesos = 0;
        for (int index=0 ; index < hourCounts.length ; index++)
        {
            contadorAccesos = contadorAccesos +  hourCounts[index];  
        }
        return contadorAccesos;
    }

    /**
     *  Método que devuelve en qué hora el servidor
     *  tuvo que responder a más peticiones. Si hay empate devuelve la última de las horas. 
     *  Si no ha habido accesos informa del hecho por pantalla y devuelve -1.
     */
    public int busiestHour()
    {
        int horaMasPeticiones = 0;
        int comparador = 0;
        for (int index=0 ; index < hourCounts.length ; index++)
        {
            if (hourCounts[index] >= comparador)
            {
                comparador =hourCounts[index];
                horaMasPeticiones = index;
                
            }
            

        }
        if (comparador == 0)
        {
          System.out.println("No ha habido accesos");  
          horaMasPeticiones = -1;  
        }
        return horaMasPeticiones;
    }
    /**
     * Metodo que devuelve la hora a la que el servidor estuvo menos sobrecargado. Si hay empate devuelve 
     * la última de las horas.  Si no ha habido accesos informa del hecho por pantalla y devuelve -1.
     */
    public int quietestHour()
    {
        int horaMenosPeticiones = 0;
        int comparador =  hourCounts[busiestHour()];
        for (int index=0 ; index < hourCounts.length ; index++)
        {
            if (hourCounts[index] <= comparador)
            {
                comparador = hourCounts[index];
                horaMenosPeticiones = index;
                
            }
            

        }
        if (comparador == 0)
        {
          System.out.println("No ha habido accesos");
          horaMenosPeticiones = -1;  
        }
        return horaMenosPeticiones;
    
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while(hour < hourCounts.length)
        {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++;
        }

    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
