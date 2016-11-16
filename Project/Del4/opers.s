# Code file created by Pascal2016 compiler 2016-11-08 14:49:19
        .globl  main                    
main:
        call    prog$operatortest_1     # Start program
        movl    $0,%eax                 # Set status 0 and
        ret                             # terminate the program
proc$test_3:
        enter   $32,$3                  # Start of test
        movl    $110,%eax               #   'n'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $111,%eax               #   'o'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $116,%eax               #   't'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        xorl    $0x1,%eax               #   not
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        leave                           # End of test
        ret                             
proc$testunaryboolean_2:
        enter   $32,$2                  # Start of testunaryboolean
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #1.
        call    proc$test_3             
        addl    $4,%esp                 # Pop params.
        movl    $1,%eax                 #   1
        pushl   %eax                    # Push param #1.
        call    proc$test_3             
        addl    $4,%esp                 # Pop params.
        leave                           # End of testunaryboolean
        ret                             
proc$test_5:
        enter   $32,$3                  # Start of test
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $97,%eax                #   'a'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $110,%eax               #   'n'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $100,%eax               #   'd'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        movl    %eax,%ecx               
        popl    %eax                    
        andl    %ecx,%eax               #   and
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $111,%eax               #   'o'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $114,%eax               #   'r'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        movl    %eax,%ecx               
        popl    %eax                    
        orl     %ecx,%eax               #   or
        pushl   %eax                    # Push next param.
        call    write_bool              
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        leave                           # End of test
        ret                             
proc$testbinaryboolean_4:
        enter   $32,$2                  # Start of testbinaryboolean
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #2.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #1.
        call    proc$test_5             
        addl    $8,%esp                 # Pop params.
        movl    $1,%eax                 #   1
        pushl   %eax                    # Push param #2.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #1.
        call    proc$test_5             
        addl    $8,%esp                 # Pop params.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #2.
        movl    $1,%eax                 #   1
        pushl   %eax                    # Push param #1.
        call    proc$test_5             
        addl    $8,%esp                 # Pop params.
        movl    $1,%eax                 #   1
        pushl   %eax                    # Push param #2.
        movl    $1,%eax                 #   1
        pushl   %eax                    # Push param #1.
        call    proc$test_5             
        addl    $8,%esp                 # Pop params.
        leave                           # End of testbinaryboolean
        ret                             
proc$test_7:
        enter   $32,$3                  # Start of test
        movl    $45,%eax                #   '-'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        negl    %eax                    #   - (prefix)
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $43,%eax                #   '+'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        leave                           # End of test
        ret                             
proc$testunarynumeric_6:
        enter   $32,$2                  # Start of testunarynumeric
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #1.
        call    proc$test_7             
        addl    $4,%esp                 # Pop params.
        movl    $11,%eax                #   11
        negl    %eax                    #   - (prefix)
        pushl   %eax                    # Push param #1.
        call    proc$test_7             
        addl    $4,%esp                 # Pop params.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #1.
        call    proc$test_7             
        addl    $4,%esp                 # Pop params.
        leave                           # End of testunarynumeric
        ret                             
proc$test_9:
        enter   $32,$3                  # Start of test
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $43,%eax                #   '+'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #   +
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $45,%eax                #   '-'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #   -
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $42,%eax                #   '*'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #   *
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
                                        # Start if-statement
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    
        movl    $0,%eax                 #   0
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setne   %al                     # Test <>
        cmpl    $0,%eax                 
        je      .L0010                  
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $100,%eax               #   'd'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $105,%eax               #   'i'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $118,%eax               #   'v'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #   /
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $109,%eax               #   'm'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $111,%eax               #   'o'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $100,%eax               #   'd'
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $61,%eax                #   '='
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    $32,%eax                #   ' '
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
        movl    -12(%ebp),%edx          
        movl    8(%edx),%eax            #   x
        pushl   %eax                    
        movl    -12(%ebp),%edx          
        movl    12(%edx),%eax           #   y
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #   mod
        pushl   %eax                    # Push next param.
        call    write_int               
        addl    $4,%esp                 # Pop param.
        movl    $10,%eax                #   10
        pushl   %eax                    # Push next param.
        call    write_char              
        addl    $4,%esp                 # Pop param.
.L0010:
                                        # End if-statement
        leave                           # End of test
        ret                             
proc$testbinarynumeric_8:
        enter   $32,$2                  # Start of testbinarynumeric
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #2.
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $11,%eax                #   11
        negl    %eax                    #   - (prefix)
        pushl   %eax                    # Push param #2.
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #2.
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #2.
        movl    $11,%eax                #   11
        negl    %eax                    #   - (prefix)
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $11,%eax                #   11
        negl    %eax                    #   - (prefix)
        pushl   %eax                    # Push param #2.
        movl    $11,%eax                #   11
        negl    %eax                    #   - (prefix)
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #2.
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $17,%eax                #   17
        pushl   %eax                    # Push param #2.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $11,%eax                #   11
        negl    %eax                    #   - (prefix)
        pushl   %eax                    # Push param #2.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #2.
        movl    $0,%eax                 #   0
        pushl   %eax                    # Push param #1.
        call    proc$test_9             
        addl    $8,%esp                 # Pop params.
        leave                           # End of testbinarynumeric
        ret                             
prog$operatortest_1:
        enter   $32,$1                  # Start of operatortest
        call    proc$testunaryboolean_2 
        call    proc$testunarynumeric_6 
        call    proc$testbinaryboolean_4 
        call    proc$testbinarynumeric_8 
        leave                           # End of operatortest
        ret                             
