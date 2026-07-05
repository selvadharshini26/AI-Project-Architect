/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        canvas: '#0A1A2B',      // deep drafting-table navy
        panel: '#0E2238',       // slightly lighter navy for nav/panels
        line: 'rgba(255,255,255,0.06)',
        brass: '#C89B3C',       // drafting-instrument brass accent
        'brass-bright': '#E3B857',
        paper: '#F3EFE4',       // unrolled blueprint paper
        'paper-dim': '#E7E1D2',
        ink: '#1B2430',         // text on paper
        slate: '#7C93AD',       // muted text on navy
        'slate-light': '#B7C6DA',
        danger: '#D96C6C',
        success: '#6FBF8B',
      },
      fontFamily: {
        display: ['"Space Grotesk"', 'sans-serif'],
        body: ['Inter', 'sans-serif'],
        mono: ['"IBM Plex Mono"', 'monospace'],
      },
      backgroundImage: {
        grid:
          'linear-gradient(rgba(255,255,255,0.05) 1px, transparent 1px), linear-gradient(90deg, rgba(255,255,255,0.05) 1px, transparent 1px)',
      },
      backgroundSize: {
        grid: '32px 32px',
      },
    },
  },
  plugins: [],
};
