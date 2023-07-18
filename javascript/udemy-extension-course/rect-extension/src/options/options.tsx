import React from 'react'
import ReactDOM from 'react-dom/client'
import './options.css'

const Option = () => <h1>Option</h1>

const div = document.createElement('div')
document.body.appendChild(div)

const root = ReactDOM.createRoot(div)

root.render(
  <React.StrictMode>
    <Option />
  </React.StrictMode>
)
