import { useEffect } from "react";
import { useLocation } from "react-router";

// React Router keeps scroll position between pages by default, so without this you'd land mid-page
// after navigating (i.e. at the bottom if you clicked a link at the bottom of the previous page).
export default function ScrollToTop() {
    const { pathname } = useLocation();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, [pathname]);

    return null;
}
