# Code file created by Pascal2016 compiler 2016-11-08 14:49:26
        .globl  main                    
main:
        call    prog$primes_1           # Start program
        movl    $0,%eax                 # Set status 0 and
        ret                             # terminate the program
proc$findprimes_2:
        enter   $40,$2                  # Start of findprimes
        movl    $2,%eax                 #   2
        movl    -8(%ebp),%edx           
        movl    %eax,-36(%edx)          # i1 :=
.L0003:
                                        # Start while-statement
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i1
        pushl   %eax                    
        movl    $1000,%eax              #   1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0004                  
        movl    $2,%eax                 #   2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i1
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #   *
        movl    -8(%ebp),%edx           
        movl    %eax,-40(%edx)          # i2 :=
.L0005:
                                        # Start while-statement
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #   i2
        pushl   %eax                    
        movl    $1000,%eax              #   1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0006                  
        movl    $0,%eax                 #   0
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #   i2
        subl    $2,%eax                 
        movl    -4(%ebp),%edx           
        leal    -4028(%edx),%edx        
        popl    %ecx                    
        movl    %ecx,0(%edx,%eax,4)     # prime[x] :=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #   i2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #   +
        movl    -8(%ebp),%edx           
        movl    %eax,-40(%edx)          # i2 :=
        jmp     .L0005                  
.L0006:
                                        # End while-statement
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i1
        pushl   %eax                    
        movl    $1,%eax                 #   1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #   +
        movl    -8(%ebp),%edx           
        movl    %eax,-36(%edx)          # i1 :=
        jmp     .L0003                  
.L0004:
                                        # End while-statement
        leave                           # End of findprimes
        ret                             
proc$p4_7:
        enter   $32,$2                  # Start of p4
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    $1000,%eax              #   1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0008                  
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
.L0008:
                                        # End if-statement
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    $100,%eax               #   100
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0009                  
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
.L0009:
                                        # End if-statement
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    $10,%eax                #   10
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0010                  
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
.L0010:
                                        # End if-statement
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        leave                           # End of p4
        ret                             
proc$printprimes_11:
        enter   $40,$2                  # Start of printprimes
        movl    $2,%eax                 #   2
        movl    -8(%ebp),%edx           
        movl    %eax,-36(%edx)          # i :=
        movl    $0,%eax                 #   0
        movl    -8(%ebp),%edx           
        movl    %eax,-40(%edx)          # nprinted :=
.L0012:
                                        # Start while-statement
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i
        pushl   %eax                    
        movl    $1000,%eax              #   1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0013                  
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i
        subl    $2,%eax                 
        movl    -4(%ebp),%edx           
        leal    -4028(%edx),%edx        
        movl    0(%edx,%eax,4),%eax     #   prime[...]
        cmpl    $0,%eax                 
        je      .L0014                  
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #   nprinted
        pushl   %eax                    
        movl    $0,%eax                 #   0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setg    %al                     # Test >
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #   nprinted
        pushl   %eax                    
        movl    $10,%eax                #   10
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #   mod
        pushl   %eax                    
        movl    $0,%eax                 #   0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test =
        movl    %eax,%ecx               
        popl    %eax                    
        andl    %ecx,%eax               #   and
        cmpl    $0,%eax                 
        je      .L0015                  
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
.L0015:
                                        # End if-statement
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i
        pushl   %eax                    # Push param #1.
        call    proc$p4_7               
        addl    $4,%esp                 # Pop params.
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #   nprinted
        pushl   %eax                    
        movl    $1,%eax                 #   1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #   +
        movl    -8(%ebp),%edx           
        movl    %eax,-40(%edx)          # nprinted :=
.L0014:
                                        # End if-statement
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #   i
        pushl   %eax                    
        movl    $1,%eax                 #   1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #   +
        movl    -8(%ebp),%edx           
        movl    %eax,-36(%edx)          # i :=
        jmp     .L0012                  
.L0013:
                                        # End while-statement
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        leave                           # End of printprimes
        ret                             
prog$primes_1:
        enter   $4032,$1                # Start of primes
        movl    $2,%eax                 #   2
        movl    -4(%ebp),%edx           
        movl    %eax,-4032(%edx)        # i :=
.L0016:
                                        # Start while-statement
        movl    -4(%ebp),%edx           
        movl    -4032(%edx),%eax        #   i
        pushl   %eax                    
        movl    $1000,%eax              #   1000
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0017                  
        movl    $1,%eax                 #   1
        pushl   %eax                    
        movl    -4(%ebp),%edx           
        movl    -4032(%edx),%eax        #   i
        subl    $2,%eax                 
        movl    -4(%ebp),%edx           
        leal    -4028(%edx),%edx        
        popl    %ecx                    
        movl    %ecx,0(%edx,%eax,4)     # prime[x] :=
        movl    -4(%ebp),%edx           
        movl    -4032(%edx),%eax        #   i
        pushl   %eax                    
        movl    $1,%eax                 #   1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #   +
        movl    -4(%ebp),%edx           
        movl    %eax,-4032(%edx)        # i :=
        jmp     .L0016                  
.L0017:
                                        # End while-statement
        call    proc$findprimes_2       
        call    proc$printprimes_11     
        leave                           # End of primes
        ret                             
