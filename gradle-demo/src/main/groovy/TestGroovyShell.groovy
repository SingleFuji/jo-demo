class TestGroovyShell {
    static void main(args){
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        Script scrpt = shell.parse(new File("src/main/groovy/Temp.groovy"));

        binding.setVariable("str1", "foo");
        binding.setVariable("str2", "boo");
        binding.setVariable("tools", scrpt);

        System.out.println(shell.evaluate("tools.printStr(str1, str2)"));
        System.out.println(shell.evaluate("str1.concat(str2)"));
    }
}
