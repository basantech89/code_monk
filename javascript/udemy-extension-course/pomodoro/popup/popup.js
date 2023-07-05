let tasks = []

const updateTime = () => {
  chrome.storage.local.get(['timer', 'timeOption'], res => {
    const time = document.getElementById('time')
    const minutes = `${res.timeOption - Math.ceil(res.timer / 60)}`.padStart(2, '0')
    const seconds = res.timer ? `${60 - res.timer % 60}`.padStart(2, '0') : '00'
    time.textContent = `${minutes}:${seconds}`
  })
}

updateTime()
setInterval(updateTime, 1000)

const startTimerBtn = document.getElementById('start-timer-btn')
startTimerBtn.addEventListener('click', () => {
  chrome.storage.local.get(['isRunning'], res => {
    chrome.storage.local.set({ isRunning: !res.isRunning }, () => {
      startTimerBtn.textContent = !res.isRunning ? 'Pause Timer' : 'Start Timer'; 
    })
  })
})

const resetTimerBtn = document.getElementById('reset-timer-btn')
resetTimerBtn.addEventListener('click', () => {
  chrome.storage.local.set({
    timer: 0,
    isRunning: false
  }, () => {
    startTimerBtn.textContent = 'Start Timer'
  })
})

const addTaskBtn = document.getElementById('add-task-btn');
addTaskBtn.addEventListener('click', () => addTask())

chrome.storage.sync.get(['tasks'], res => {
  tasks = res.tasks ?? []
  renderTasks()
})

const saveTasks = () => chrome.storage.sync.set({ tasks })

const renderTasks = () => {
  const taskContainer = document.getElementById('task-container')
  taskContainer.textContent = ''
  tasks.forEach((taskText, taskNum) => {
    renderTask(taskNum)
  })
}

const deleteTask = (taskNum) => {
  tasks.splice(taskNum, 1)
  renderTasks()
  saveTasks()
}

function renderTask(taskNum) {
  const taskRow = document.createElement('div');

  const text = document.createElement('input');
  text.type = 'text';
  text.placeholder = 'Enter a task...'
  text.value = tasks[taskNum]
  text.className = 'task-input'
  text.addEventListener('change', () => {
    tasks[taskNum] = text.value
    saveTasks()
  })

  const deleteBtn = document.createElement('input');
  deleteBtn.type = 'button';
  deleteBtn.value = 'X'
  deleteBtn.className = 'task-delete'
  deleteBtn.addEventListener('click', () => {
    deleteTask(taskNum)
  })

  taskRow.appendChild(text)
  taskRow.appendChild(deleteBtn)

  const taskContainer = document.getElementById('task-container')
  taskContainer.appendChild(taskRow)
}

function addTask() {
  const taskLength = tasks.length
  tasks.push('');
  renderTask(taskLength)
  saveTasks()
}