/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["../resources/templates/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
          customGray: "#121212",
          customPink: "#fccce0",
      },
    },
  },
  plugins: [],
}