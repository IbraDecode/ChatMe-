package ibradecode.chatme

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.SupabaseClient

object SupabaseHelper {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://adixxrhdmrzeugxxviyx.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFkaXh4cmhkbXJ6ZXVneHh2aXl4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTcwNjYzMjQsImV4cCI6MjA3MjY0MjMyNH0.4igAI_dD46YoAcqfrZ3vrzDrt0Fs1xyIsqeKcUgvLFo"
    ) {
        install(Storage)
    }
}


