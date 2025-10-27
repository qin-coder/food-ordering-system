import { ThemeProvider } from '@emotion/react'
import './App.css'
import Navbar from './components/Navbar/Navbar'
import darkTheme from './theme/DarkTheme'
import { CssBaseline } from '@mui/material'

function App() {
  return (
    <h1 className='mt-0 text-center text-3xl font-bold'>
      <ThemeProvider theme={darkTheme}>
        <CssBaseline />
        <Navbar />
      </ThemeProvider>
    </h1>
  )
}

export default App
