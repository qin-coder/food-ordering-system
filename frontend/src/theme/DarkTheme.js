import { createTheme } from '@mui/material'

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#81C784',
    },
    secondary: {
      main: '#FBC02D',
    },
    background: {
      default: '#101010',
      paper: '#181818',
    },
    text: {
      primary: '#F5F5F5',
      secondary: '#BDBDBD',
    },
  },
})

export default darkTheme
