/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["../resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
          customGray: "#121212",
          customPink: "#df8eb0",
          customLightGray: "#262626",
      },
    },
  },
  plugins: [],
}