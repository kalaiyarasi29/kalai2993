import java.io.*;
import java.util.*;
import java.util.Scanner;


public class pblp {

    
    public static int pm_id[]=new int[500];
    public static int pm_memory[]=new int[500];
    public static int pm_memory_dup[]=new int[500];
    public static int pm_memory_used[]=new int[500];
    public static int pm_cpu[]=new int[500];
    public static int pm_cpu_dup[]=new int[500];
    public static int pm_cpu_used[]=new int[500];
    public static int pm_count;
    public static int pm_uti[]=new int[500];
    
   
    public static int vm_id[]=new int[500];
    public static int vm_priority[]=new int[500];
    public static int vm_memory[]=new int[500];
    public static int vm_memory_dup[]=new int[500];
    public static int vm_cpu[]=new int[500];
    public static int vm_cpu_dup[]=new int[500];
    public static int vm_count;
            
    public static int pm_usage[]=new int[500];
    public static int pm_used;
    public static int vm_handled;
    public static int number=0;

    
    
    public static void main(String[] args)throws IOException
    {
        int i,utilization,total_utilization=0,throughtput,total_throughtput=0;
        Scanner get = new Scanner(System.in);
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       
        System.out.println("Enter the number of PMs : ");
        pm_count=Integer.parseInt(br.readLine());
        createpm(pm_count);
                
        System.out.println("\nEnter the number of Virtual Machines : ");
        vm_count=Integer.parseInt(br.readLine());
        createvm(vm_count);
        
        allocation();
        
        System.out.printf("\n %-15s %-15s %-15s %-15s %-15s", "PMID", 
        "MEMORY(GB)",  "MEMORY_AVAILABLE", "CPU","CPU_AVAILABLE" );
        for(i=0;i<pm_count;i++)
        {
            
            System.out.printf("\n %-15d %-15d %-15d %-15d %15d", pm_id[i], pm_memory_dup[i], 
            pm_memory[i], pm_cpu_dup[i], pm_cpu[i]);
        }
        
        
        System.out.printf("\n%-15s%-15s%-15s%-15s","VM_ID","VM_PRIORITY", "VM_MEMORY(GB)","VM_CPU");
        for(i=0;i<vm_count;i++)
        {
            System.out.printf("\n%-15s%-15s%-15s%-15s", vm_id[i],vm_priority[i], vm_memory[i], 
            vm_cpu[i]);
        }  
        
        
        for(i=0;i<pm_count;i++)
        {
        pm_memory_used[i]=pm_memory_dup[i]-pm_memory[i];
        pm_cpu_used[i]=pm_cpu_dup[i]-pm_cpu[i];
        } 
        
        for(i=0;i<pm_count;i++)
        {
            utilization=0;
	throughtput=0;
            if(pm_memory[i]!=pm_memory_dup[i]&&pm_cpu[i]!=pm_cpu_dup[i])
            {
                pm_uti[i]=((((pm_memory_used[i])*100)/pm_memory_dup[i])+(((pm_cpu_used[i])*100)/pm_cpu_dup[i]))/2;
		throughtput=((pm_memory_used[i])*100)/pm_memory_dup[i];
                throughtput+=((pm_cpu_used[i])*100)/pm_cpu_dup[i];
                throughtput/=50;
                total_throughtput+=throughtput;
                utilization=((pm_memory_used[i])*100)/pm_memory_dup[i];
                utilization+=((pm_cpu_used[i])*100)/pm_cpu_dup[i];
                utilization/=2;
                total_utilization+=utilization;
		
		
                pm_used++;
            }
        }

        
        
        for(i=0;i<vm_count;i++)
        {
            if(vm_memory[i]!=vm_memory_dup[i])
            {
                vm_handled=vm_handled+1;
            }
        }
        
        total_utilization=(total_utilization/pm_used);
      System.out.printf("\n UTILIZATION= %s",total_utilization);
        System.out.printf("\n THROUGHTPUT= %s",total_throughtput);
        System.out.printf("\n Total VM's handled= %s",vm_handled);
        System.out.printf("\n PM's used= %s",pm_used);
        
        
    }
    
    
    public static void createpm(int count)throws FileNotFoundException
    {
        Scanner scanpmtable=new Scanner(new File("/home/cse/Desktop/pg/dataset/pm.txt"));
        int i=0,temp=0,j;
        while(scanpmtable.hasNext()&&(i)<count)
        {
            pm_memory[i]=scanpmtable.nextInt();
            pm_memory_dup[i]=pm_memory[i];
            pm_memory_used[i]=0;
            pm_cpu[i]=scanpmtable.nextInt();
            pm_cpu_dup[i]=pm_cpu[i];
            pm_id[i]=scanpmtable.nextInt();
            i++;
        }
        pm_count=i;
        System.out.printf("\n%-15s %-15s %-15s %-15s %-15s", "PMID", 
        "MEMORY(GB)",  "MEMORY_AVAILABLE", "CPU","CPU_USED" );
        for(i=0;i<pm_count;i++)
        {
            System.out.printf("\n%-15d %-15d %-15d %-15d %-15d", pm_id[i], pm_memory_dup[i], pm_memory[i], pm_cpu_dup[i], pm_cpu[i]);
        }
        for(i=0;i<pm_count;i++)
        {
            for(j=0;j<pm_count;j++)
            {
                if(pm_memory[j]>pm_memory[i])
                {
                    temp=pm_id[i];
                    pm_id[i]=pm_id[j];
                    pm_id[j]=temp;
                    
                    temp=pm_memory[i];
                    pm_memory[i]=pm_memory[j];
                    pm_memory[j]=temp;
                            
                    temp=pm_memory_dup[i];
                    pm_memory_dup[i]=pm_memory_dup[j];
                    pm_memory_dup[j]=temp;
                    
                    temp=pm_cpu[i];
                    pm_cpu[i]=pm_cpu[j];
                    pm_cpu[j]=temp;
                    
                    temp=pm_cpu_dup[i];
                    pm_cpu_dup[i]=pm_cpu_dup[j];
                    pm_cpu_dup[j]=temp;
                    
                }
            }
        }
        System.out.printf("\n %-15s %-15s %-15s %-15s %-15s", "PMID", 
        "MEMORY(GB)",  "MEMORY_AVAILABLE", "CPU","CPU_AVAILABLE" );
        for(i=0;i<pm_count;i++)
        {
            
            System.out.printf("\n %-15d %-15d %-15d %-15d %15d", pm_id[i], pm_memory_dup[i], 
            pm_memory[i], pm_cpu_dup[i], pm_cpu[i]);
        }
        System.out.printf("\n%-15s%-15s%-15s%-15s","VM_ID","VM_PRIORITY", "VM_MEMORY(GB)","VM_CPU");
        for(i=0;i<vm_count;i++)
        {
            System.out.printf("\n%-15s%-15s%-15s%-15s", vm_id[i],vm_priority[i], vm_memory[i], 
            vm_cpu[i]);
        }  
    }
    
    public static void createvm(int count) throws FileNotFoundException
    {

        Scanner scan_vmtable=new Scanner(new File("/home/cse/Desktop/pg/dataset/vm.txt"));
        int i=0,j,temp;
        while(scan_vmtable.hasNext()&&(i)<count)
        {            
            vm_id[i]=i;
            vm_memory[i]=scan_vmtable.nextInt();
            vm_memory_dup[i]=vm_memory[i];
            vm_cpu[i]=scan_vmtable.nextInt();
            vm_cpu_dup[i]=vm_cpu_dup[i];
            vm_priority[i]=scan_vmtable.nextInt();
            i++;
            
        }
        count=i;
        System.out.printf("\n%-15s%-15s%-15s%-15s","VM_ID","VM_PRIORITY", "VM_MEMORY(GB)","VM_CPU");
        for(i=0;i<vm_count;i++)
        {
            System.out.printf("\n%-15s%-15s%-15s%-15s",vm_id[i], vm_priority[i], vm_memory[i], 
            vm_cpu[i]);
        }
        
        
        for(i=0;i<vm_count;i++)
        {
            for(j=0;j<vm_count;j++)
            {
                if(vm_priority[j]<vm_priority[i])
                {
                    temp=vm_priority[i];
                    vm_priority[i]=vm_priority[j];
                    vm_priority[j]=temp;
                    
                    temp=vm_id[i];
                    vm_id[i]=vm_id[j];
                    vm_id[j]=temp;
                    
                    temp=vm_memory[i];
                    vm_memory[i]=vm_memory[j];
                    vm_memory[j]=temp;
                            
                    temp=vm_memory_dup[i];
                    vm_memory_dup[i]=vm_memory_dup[j];
                    vm_memory_dup[j]=temp;
                    
                    temp=vm_cpu[i];
                    vm_cpu[i]=vm_cpu[j];
                    vm_cpu[j]=temp;
                    
                    temp=vm_cpu_dup[i];
                    vm_cpu_dup[i]=vm_cpu_dup[j];
                    vm_cpu_dup[j]=temp;
                    
                }
            }
        }
        System.out.printf("\n%-15s%-15s%-15s%-15s","VM_ID","VM_PRIORITY", "VM_MEMORY(GB)","VM_CPU");
        for(i=0;i<vm_count;i++)
        {
            System.out.printf("\n%-15s%-15s%-15s%-15s", vm_id[i],vm_priority[i], vm_memory[i], 
            vm_cpu[i]);
        }    
        
    }
    
    public static void allocation()
    {
        int i;
        for(i=0;i<vm_count;i++)
        {
            number=0;
            checking(i);
            if(number==0)
            {
                selection(i);
            }
        }
    }
    
    
    public static void checking(int item)
    {
        int i,j,repeat=0;
        for(i=0;i<pm_count;i++)
        {
            if((vm_memory[item]==pm_memory[i])&&(vm_cpu[item]<=pm_cpu[i])&&(repeat!=1))
            {
                repeat=1;
                number=1;
                pm_memory[i]=pm_memory[i]-vm_memory[item];
                pm_cpu[i]=pm_cpu[i]-vm_cpu[item];
                vm_memory[item]=0;
                
                System.out.printf("\n vm id %s is allocated to pm id %s",vm_id[item],pm_id[i]);
                
                
                System.out.printf("\n %-15s %-15s %-15s %-15s %-15s", "PMID", 
        "MEMORY(GB)",  "MEMORY_AVAILABLE", "CPU","CPU_AVAILABLE" );
        for(j=0;j<pm_count;j++)
        {
             
            System.out.printf("\n %-15d %-15d %-15d %-15d %15d", pm_id[j], pm_memory_dup[j], 
            pm_memory[j], pm_cpu_dup[j], pm_cpu[j]);
        }
        
                
            }
        }
    }
    
    public static void selection(int item)
    {
        int i,j,temp=0;
        for(i=0;(i<pm_count)&&(temp!=1);i++)
        {
            if((vm_memory[item]<=pm_memory[i])&&(vm_cpu[item]<=pm_cpu[i]))
            {
                
                pm_memory[i]=pm_memory[i]-vm_memory[item];
                pm_cpu[i]=pm_cpu[i]-vm_cpu[item];
                vm_memory[item]=0;
                System.out.printf("\n vm id %s is allocated to pm id %s",vm_id[item],pm_id[i]);
            
          System.out.printf("\n %-15s %-15s %-15s %-15s %-15s", "PMID", 
        "MEMORY(GB)",  "MEMORY_AVAILABLE", "CPU","CPU_AVAILABLE" );
        for(j=0;j<pm_count;j++)
        {
            
            System.out.printf("\n %-15d %-15d %-15d %-15d %15d", pm_id[j], pm_memory_dup[j], 
            pm_memory[j], pm_cpu_dup[j], pm_cpu[j]);
        }
        System.out.printf("\n%-15s%-15s%-15s%-15s","VM_ID","VM_PRIORITY", "VM_MEMORY(GB)","VM_CPU");
        for(j=0;j<vm_count;j++)
        {
            System.out.printf("\n%-15s%-15s%-15s%-15s", vm_id[j],vm_priority[j], vm_memory[j], 
            vm_cpu[j]);
        } 
        temp=1;
            }
            else 
            {
                if((vm_memory[item]>pm_memory[i])&&(pm_memory[i]!=0)&&(vm_cpu[item]>pm_cpu[i])&&(pm_cpu[i]!=0))
                {
                    
                    vm_memory[item]=vm_memory[item]-pm_memory[i];
                    pm_memory[i]=0;
                    vm_cpu[item]=vm_cpu[item]-pm_cpu[i];
                    pm_cpu[i]=0;
                    
                
               
        }
                    System.out.printf("\n vm id %s is allocated to pm id %s",vm_id[item],pm_id[i]);
                
                
            System.out.printf("\n %-15s %-15s %-15s %-15s %-15s", "PMID", 
        "MEMORY(GB)",  "MEMORY_AVAILABLE", "CPU","CPU_AVAILABLE" );
        for(j=0;j<pm_count;j++)
        {
            
            System.out.printf("\n %-15d %-15d %-15d %-15d %15d", pm_id[j], pm_memory_dup[j], 
            pm_memory[j], pm_cpu_dup[j], pm_cpu[j]);
        }
        System.out.printf("\n%-15s%-15s%-15s%-15s","VM_ID","VM_PRIORITY", "VM_MEMORY(GB)","VM_CPU");
        for(j=0;j<vm_count;j++)
        {
            System.out.printf("\n%-15s%-15s%-15s%-15s", vm_id[j],vm_priority[j], vm_memory[j], 
            vm_cpu[j]);
        }  
                }
            }
        }
    }
    

