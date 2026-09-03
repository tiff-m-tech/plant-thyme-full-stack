import { useEffect } from "react";

export function usePageTitleForBrowserTab(title) {
    useEffect(() => {
        document.title = `${title} | Plant Thyme`;
    }, [title]);
}
