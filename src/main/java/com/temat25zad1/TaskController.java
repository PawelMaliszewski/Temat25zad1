package com.temat25zad1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Task task, RedirectAttributes redirectAttributes) {
        taskRepository.save(task);
        redirectAttributes.addFlashAttribute("status", task.getTaskStatus());
        return "redirect:list";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "status", required = false) TaskStatus status, Model model) {
        TaskStatus taskStatus = (model.containsAttribute("status")) ? (TaskStatus) model.getAttribute("status") : status;
        List<Task> tasks = (taskStatus != null) ? taskRepository.findAllByTaskStatusEqualsOrderByTaskDeadLine(taskStatus) :
                taskRepository.findAll();
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("tasks", tasks);
        return "list";
    }

    @GetMapping("/updateById")
    public String saveTransaction(@RequestParam int id, Model model) {
        Optional<Task> task = taskRepository.findById((long) id);
        task.ifPresent(value -> model.addAttribute("task", task.get()));
        return "add";
    }

    @GetMapping("/setdone")
    public String setDone(@RequestParam long id, RedirectAttributes redirectAttributes) {
        Optional<Task> optional = taskRepository.findById(id);
        TaskStatus status = null;
        if (optional.isPresent()) {
            Task task = optional.get();
            status = task.getTaskStatus();
            task.setTaskStatus(TaskStatus.DONE);
            taskRepository.save(task);
        }
        redirectAttributes.addAttribute("status", status);
        return "redirect:list";
    }

    @GetMapping("/deletebyid")
    String deleteById(@RequestParam long id, @RequestParam("status") TaskStatus status, RedirectAttributes redirectAttributes) {
        taskRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("status", status);
        return "redirect:list";
    }
}
