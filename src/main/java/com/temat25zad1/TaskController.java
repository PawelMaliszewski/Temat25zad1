package com.temat25zad1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/add")
    public String addTask(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Task task, Model model) {
        taskRepository.save(task);
        return list(task.getTaskStatus(), model);
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("tasks", tasks);
        return "/list";
    }

    @GetMapping("/listbystatus")
    public String list(@RequestParam("status") TaskStatus status, Model model) {
        List<Task> tasks = taskRepository.findAllByTaskStatusEqualsOrderByTaskDeadLine(status);
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("tasks", tasks);
        return "/list";
    }

    @GetMapping("/updateById")
    ModelAndView saveTransaction(@RequestParam int id) {
        ModelAndView modelAndView = new ModelAndView("add");
        Optional<Task> task = taskRepository.findById((long) id);
        task.ifPresent(value -> modelAndView.addObject("task", task.get()));
        task.ifPresent(value -> modelAndView.addObject("localDate", value.getTaskDeadLine()));
        return modelAndView;
    }

    @GetMapping("/setdone")
    public String setDone(@RequestParam long id, Model model) {
        Optional<Task> optional = taskRepository.findById(id);
        TaskStatus status = null;
        Task task = null;
        if (optional.isPresent()) {
            task = optional.get();
            status = task.getTaskStatus();
            task.setTaskStatus(TaskStatus.DONE);
            taskRepository.save(task);
        }
        model.addAttribute("tasks", taskRepository.findAll());
        return list(status, model);
    }

    @GetMapping("/deletebyid")
    String deleteById(@RequestParam long id, @RequestParam("status") TaskStatus status, Model model) {
        taskRepository.deleteById(id);
        return list(status, model);
    }
}
